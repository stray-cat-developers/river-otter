package io.mustelidae.riverotter.domain.client.korea.government

class GovernmentOpenResources {

    class Reply {
        data class Holiday(
            val response: Response,
        ) {
            data class Response(
                val body: Body,
                val header: Header,
            ) {
                data class Body(
                    val items: Items,
                    val numOfRows: Int,
                    val pageNo: Int,
                    val totalCount: Int,
                ) {
                    data class Items(
                        val item: List<Item>,
                    ) {
                        data class Item(
                            val dateKind: String,
                            val dateName: String,
                            val isHoliday: String,
                            val locdate: Int,
                            val seq: Int,
                        )
                    }
                }

                data class Header(
                    val resultCode: String,
                    val resultMsg: String,
                )
            }
        }
    }
}
