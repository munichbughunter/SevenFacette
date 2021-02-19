package db

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class Fruit {

    @JsonProperty("ID")
    private var id: Int = 0

    @JsonProperty("NAME")
    private var name: String? = null

    @JsonProperty("ID")
    fun getId(): Int? {
        return id
    }

    @JsonProperty("ID")
    fun setId(id: Int) {
        this.id = id
    }

    @JsonProperty("NAME")
    fun getName(): String? {
        return name
    }

    @JsonProperty("NAME")
    fun setName(name: String) {
        this.name = name
    }
}
