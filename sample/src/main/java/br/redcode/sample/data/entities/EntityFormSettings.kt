package br.redcode.sample.data.entities

import androidx.room.*

@Entity(
        tableName = "form_settings",
        foreignKeys = [
            ForeignKey(
                    entity = EntityForm::class,
                    parentColumns = arrayOf("form_id"),
                    childColumns = arrayOf("form_id"),
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            )
        ],
        indices = [
            Index(
                    value = ["form_id"],
                    unique = true
            )
        ]
)
data class EntityFormSettings(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "form_settings_id") val idFormSettings: Long = 0,

        @ColumnInfo(name = "form_id") val idForm: Long,

        val showIndicatorError: Boolean = true,
        val showIndicatorInformation: Boolean = true,
        val showSymbolRequired: Boolean = true,
        val editable: Boolean = true,
        val backgroundColor: String? = null

)