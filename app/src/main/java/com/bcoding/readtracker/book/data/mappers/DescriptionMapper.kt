package com.bcoding.readtracker.book.data.mappers

import com.bcoding.readtracker.book.data.dto.BookWorkDto
import com.bcoding.readtracker.book.domain.model.Description

fun BookWorkDto.toDescription(): Description {
    return Description(
        description = description.orEmpty()
    )
}
