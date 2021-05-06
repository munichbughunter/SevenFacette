package utils

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
internal class User(name: String, genderEnum: GenderEnum) {

    private var name: String
    private var gender: GenderEnum
    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getGenderEnum(): GenderEnum {
        return gender
    }

    fun setGenderEnum(gender: GenderEnum) {
        this.gender = gender
    }

    init {
        this.name = name
        this.gender = genderEnum
    }
}
