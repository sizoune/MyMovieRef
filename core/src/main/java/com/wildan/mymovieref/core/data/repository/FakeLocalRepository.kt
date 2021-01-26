package com.wildan.mymovieref.core.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wildan.mymovieref.core.data.local.FavoriteMoviesEntity
import com.wildan.mymovieref.core.data.local.FavoriteTVSeriesEntity

object FakeLocalRepository {


    private var gson = Gson()
    private val movieListType = object : TypeToken<List<FavoriteMoviesEntity>>() {}.type
    private val seriesListType = object : TypeToken<List<FavoriteTVSeriesEntity>>() {}.type

    fun loadFavoriteMovies(): List<FavoriteMoviesEntity> {
        return gson.fromJson(dummyFavMovies(), movieListType)
    }

    fun loadFavoriteTV(): List<FavoriteTVSeriesEntity> {
        return gson.fromJson(dummyFavTV(), seriesListType)
    }

    private fun dummyFavMovies(): String {
        return "[{\"backdrop_path\":\"/wu1uilmhM4TdluKi2ytfz8gidHf.jpg\",\"genres\":[{\"id\":16,\"name\":\"Animation\"},{\"id\":14,\"name\":\"Fantasy\"},{\"id\":12,\"name\":\"Adventure\"},{\"id\":35,\"name\":\"Comedy\"},{\"id\":10751,\"name\":\"Family\"}],\"id\":400160,\"original_title\":\"The SpongeBob Movie: Sponge on the Run\",\"overview\":\"When his best friend Gary is suddenly snatched away, SpongeBob takes Patrick on a madcap mission far beyond Bikini Bottom to save their pink-shelled pal.\",\"poster_path\":\"/jlJ8nDhMhCYJuzOw3f52CP1W8MW.jpg\",\"release_date\":\"2020-08-14\"},{\"backdrop_path\":\"/2Fk3AB8E9dYIBc2ywJkxk8BTyhc.jpg\",\"genres\":[{\"id\":28,\"name\":\"Action\"},{\"id\":53,\"name\":\"Thriller\"}],\"id\":524047,\"original_title\":\"Greenland\",\"overview\":\"John Garrity, his estranged wife and their young son embark on a perilous journey to find sanctuary as a planet-killing comet hurtles toward Earth. Amid terrifying accounts of cities getting levelled, the Garrity\\u0027s experience the best and worst in humanity. As the countdown to the global apocalypse approaches zero, their incredible trek culminates in a desperate and last-minute flight to a possible safe haven.\",\"poster_path\":\"/bNo2mcvSwIvnx8K6y1euAc1TLVq.jpg\",\"release_date\":\"2020-07-29\"},{\"backdrop_path\":\"/5UkzNSOK561c2QRy2Zr4AkADzLT.jpg\",\"genres\":[{\"id\":878,\"name\":\"Science Fiction\"},{\"id\":53,\"name\":\"Thriller\"},{\"id\":18,\"name\":\"Drama\"}],\"id\":528085,\"original_title\":\"2067\",\"overview\":\"A lowly utility worker is called to the future by a mysterious radio signal, he must leave his dying wife to embark on a journey that will force him to face his deepest fears in an attempt to change the fabric of reality and save humankind from its greatest environmental crisis yet.\",\"poster_path\":\"/7D430eqZj8y3oVkLFfsWXGRcpEG.jpg\",\"release_date\":\"2020-10-01\"},{\"backdrop_path\":\"/gnf4Cb2rms69QbCnGFJyqwBWsxv.jpg\",\"genres\":[{\"id\":53,\"name\":\"Thriller\"},{\"id\":28,\"name\":\"Action\"},{\"id\":18,\"name\":\"Drama\"},{\"id\":80,\"name\":\"Crime\"}],\"id\":671039,\"original_title\":\"Bronx\",\"overview\":\"Caught in the crosshairs of police corruption and Marseille’s warring gangs, a loyal cop must protect his squad by taking matters into his own hands.\",\"poster_path\":\"/9HT9982bzgN5on1sLRmc1GMn6ZC.jpg\",\"release_date\":\"2020-10-30\"},{\"backdrop_path\":\"/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg\",\"genres\":[{\"id\":28,\"name\":\"Action\"},{\"id\":53,\"name\":\"Thriller\"}],\"id\":724989,\"original_title\":\"Hard Kill\",\"overview\":\"The work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.\",\"poster_path\":\"/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg\",\"release_date\":\"2020-10-23\"}]"
    }

    private fun dummyFavTV(): String {
        return "[{\"backdrop_path\":\"/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg\",\"firstAirDate\":\"2005-03-27\",\"genres\":[{\"id\":18,\"name\":\"Drama\"}],\"id\":1416,\"originalName\":\"Grey\\u0027s Anatomy\",\"overview\":\"Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.\",\"poster_path\":\"/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg\"},{\"backdrop_path\":\"/ta5oblpMlEcIPIS2YGcq9XEkWK2.jpg\",\"firstAirDate\":\"2016-01-25\",\"genres\":[{\"id\":80,\"name\":\"Crime\"},{\"id\":10765,\"name\":\"Sci-Fi \\u0026 Fantasy\"}],\"id\":63174,\"originalName\":\"Lucifer\",\"overview\":\"Bored and unhappy as the Lord of Hell, Lucifer Morningstar abandoned his throne and retired to Los Angeles, where he has teamed up with LAPD detective Chloe Decker to take down criminals. But the longer he\\u0027s away from the underworld, the greater the threat that the worst of humanity could escape.\",\"poster_path\":\"/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg\"},{\"backdrop_path\":\"/9hvhGtcsGhQY58pukw8w55UEUbL.jpg\",\"firstAirDate\":\"2017-01-26\",\"genres\":[{\"id\":18,\"name\":\"Drama\"},{\"id\":9648,\"name\":\"Mystery\"}],\"id\":69050,\"originalName\":\"Riverdale\",\"overview\":\"Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.\",\"poster_path\":\"/4X7o1ssOEvp4BFLim1AZmPNcYbU.jpg\"},{\"backdrop_path\":\"/iDbIEpCM9nhoayUDTwqFL1iVwzb.jpg\",\"firstAirDate\":\"2017-09-25\",\"genres\":[{\"id\":18,\"name\":\"Drama\"}],\"id\":71712,\"originalName\":\"The Good Doctor\",\"overview\":\"A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn\\u0027t have the ability to relate to people actually save their lives?\",\"poster_path\":\"/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg\"},{\"backdrop_path\":\"/9ijMGlJKqcslswWUzTEwScm82Gs.jpg\",\"firstAirDate\":\"2019-11-12\",\"genres\":[{\"id\":10765,\"name\":\"Sci-Fi \\u0026 Fantasy\"},{\"id\":10759,\"name\":\"Action \\u0026 Adventure\"},{\"id\":37,\"name\":\"Western\"}],\"id\":82856,\"originalName\":\"The Mandalorian\",\"overview\":\"After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.\",\"poster_path\":\"/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg\"}]"
    }


}