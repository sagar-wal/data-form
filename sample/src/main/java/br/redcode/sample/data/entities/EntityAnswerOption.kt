package br.redcode.sample.data.entities

import androidx.room.*

@Entity(
        tableName = "answer_options",
        foreignKeys = [
            ForeignKey(
                    entity = EntityAnswer::class,
                    parentColumns = arrayOf("answer_id"),
                    childColumns = arrayOf("answer_id"),
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            )
            ,
            ForeignKey(
                    entity = EntityQuestionOption::class,
                    parentColumns = arrayOf("question_option_id"),
                    childColumns = arrayOf("question_option_id"),
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            )
        ],
        indices = [
            Index(
                    value = ["answer_id"],
                    unique = true
            )
        ]
)
data class EntityAnswerOption(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "answer_option_id") val idAnswerOption: Long = 0,

        @ColumnInfo(name = "answer_id") val idAnswer: Long,

        @ColumnInfo(name = "question_option_id") val idQuestionOption: Long

) {
    fun toModel() = idQuestionOption
}