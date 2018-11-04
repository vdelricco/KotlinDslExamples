package com.delricco.kotlindslexample


@DslMarker annotation class CharacterDsl

fun character(lambda: CharacterBuilder.() -> Unit) = CharacterBuilder().apply(lambda).build()

data class Character(val name: String, val weapon: Weapon)
data class Weapon(val type: String, val damage: Float)

@CharacterDsl class CharacterBuilder {
    var name = ""
    private var weapon = Weapon("", 0f)

    fun weapon(lambda: WeaponBuilder.() -> Unit) { weapon = WeaponBuilder().apply(lambda).build() }
    fun build() = Character(name, weapon)
}

@CharacterDsl class WeaponBuilder {
    var type = ""
    var damage = 0f

    fun build() = Weapon(type, damage)
}
