package com.delricco.kotlindslexample

import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterTest {

    @Test
    fun `Assert values set in Character DSL`() {
        val character = character {
            name = "Chris"

            weapon {
                type = "Sword"
                damage = 15f
            }
        }

        assertEquals("Chris", character.name)
        assertEquals("Sword", character.weapon.type)
        assertEquals(15f, character.weapon.damage)
    }
}
