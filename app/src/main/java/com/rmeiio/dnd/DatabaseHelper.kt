package com.rmeiio.dnd

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dnd.db"
        private const val DATABASE_VERSION = 2

        const val TABLE_CHARACTER = "character"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_CLASS = "class"
        const val COLUMN_RACE = "race"
        const val COLUMN_FORCA = "forca"
        const val COLUMN_DESTREZA = "destreza"
        const val COLUMN_CONSTITUICAO = "constituicao"
        const val COLUMN_INTELIGENCIA = "inteligencia"
        const val COLUMN_SABEDORIA = "sabedoria"
        const val COLUMN_CARISMA = "carisma"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
           CREATE TABLE $TABLE_CHARACTER (
               $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
               $COLUMN_NAME TEXT,
               $COLUMN_CLASS TEXT,
               $COLUMN_RACE TEXT,
               $COLUMN_FORCA INTEGER DEFAULT 8,
               $COLUMN_DESTREZA INTEGER DEFAULT 8,
               $COLUMN_CONSTITUICAO INTEGER DEFAULT 8,
               $COLUMN_INTELIGENCIA INTEGER DEFAULT 8,
               $COLUMN_SABEDORIA INTEGER DEFAULT 8,
               $COLUMN_CARISMA INTEGER DEFAULT 8
              )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CHARACTER")
        onCreate(db)
    }

    fun insertCharacter(name: String, charClass: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_CLASS, charClass)
        }
        return db.insert(TABLE_CHARACTER, null, values)
    }

    fun updateCharacterRace(id: Int, race: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RACE, race)
        }

        return db.update(TABLE_CHARACTER, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun updateCharacter(id: Int, name: String, charClass: String, race: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_CLASS, charClass)
            put(COLUMN_RACE, race)
        }

        return db.update(TABLE_CHARACTER, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun deleteCharacter(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_CHARACTER, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun hasCharacter(): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT 1 FROM $TABLE_CHARACTER LIMIT 1", null)
        val hasCharacter = cursor.moveToFirst() // Retorna true se houver pelo menos um registro
        cursor.close()
        return hasCharacter
    }

    fun getLastCharacterId(): Int? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT MAX($COLUMN_ID) FROM $TABLE_CHARACTER", null)
        val lastId = if (cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            null
        }
        cursor.close()
        return lastId
    }

    fun updateCharacterAttributes(
        id: Int,
        forca: Int,
        destreza: Int,
        constituicao: Int,
        inteligencia: Int,
        sabedoria: Int,
        carisma: Int
    ): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FORCA, forca)
            put(COLUMN_DESTREZA, destreza)
            put(COLUMN_CONSTITUICAO, constituicao)
            put(COLUMN_INTELIGENCIA, inteligencia)
            put(COLUMN_SABEDORIA, sabedoria)
            put(COLUMN_CARISMA, carisma)
        }
        return db.update(TABLE_CHARACTER, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    // Método para obter os atributos do personagem
    fun getCharacterAttributes(characterId: Int): Attributes? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_CHARACTER,
            arrayOf(COLUMN_FORCA, COLUMN_DESTREZA, COLUMN_CONSTITUICAO, COLUMN_INTELIGENCIA, COLUMN_SABEDORIA, COLUMN_CARISMA),
            "$COLUMN_ID = ?",
            arrayOf(characterId.toString()),
            null,
            null,
            null
        )

        return if (cursor != null && cursor.moveToFirst()) {
            val attributes = Attributes(
                forca = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FORCA)),
                destreza = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DESTREZA)),
                constituicao = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CONSTITUICAO)),
                inteligencia = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INTELIGENCIA)),
                sabedoria = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SABEDORIA)),
                carisma = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CARISMA))
            )
            cursor.close() // Feche o cursor após obter os dados
            attributes
        } else {
            cursor?.close() // Feche o cursor se não houver dados
            null // Retorne null se não encontrar atributos
        }
    }

    fun getAllCharacters(): List<Character> {
        val db = this.readableDatabase
        val characterList = mutableListOf<Character>()
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CHARACTER", null)

        if (cursor.moveToFirst()) {
            do {
                val attributes = Attributes(
                    forca = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FORCA)),
                    destreza = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DESTREZA)),
                    constituicao = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CONSTITUICAO)),
                    inteligencia = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INTELIGENCIA)),
                    sabedoria = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SABEDORIA)),
                    carisma = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CARISMA))
                )

                val character = Character(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    charClass = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASS)),
                    race = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RACE)),
                    attributes = attributes
                )
                characterList.add(character)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return characterList
    }

    fun getCharacterById(id: Int): Character? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_CHARACTER,
            arrayOf(
                COLUMN_ID, COLUMN_NAME, COLUMN_CLASS, COLUMN_RACE,
                COLUMN_FORCA, COLUMN_DESTREZA, COLUMN_CONSTITUICAO,
                COLUMN_INTELIGENCIA, COLUMN_SABEDORIA, COLUMN_CARISMA
            ),
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        return if (cursor != null && cursor.moveToFirst()) {
            val character = Character(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                charClass = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASS)),
                race = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RACE)),
                attributes = Attributes(
                    forca = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FORCA)),
                    destreza = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DESTREZA)),
                    constituicao = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CONSTITUICAO)),
                    inteligencia = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INTELIGENCIA)),
                    sabedoria = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SABEDORIA)),
                    carisma = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CARISMA))
                )
            )
            cursor.close()
            character
        } else {
            cursor?.close()
            null
        }
    }
}

data class Attributes(
    val forca: Int,
    val destreza: Int,
    val constituicao: Int,
    val inteligencia: Int,
    val sabedoria: Int,
    val carisma: Int
)

data class Character(
    val id: Int,
    val name: String,
    val charClass: String,
    val race: String,
    val attributes: Attributes
)