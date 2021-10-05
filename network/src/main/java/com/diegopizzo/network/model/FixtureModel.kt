package com.diegopizzo.network.model

data class Status(val short: String, val elapsed: Int?)
data class Fixture(val timezone: String, val date: String, val status: Status)

data class Home(val name: String, val logo: String)
data class Away(val name: String, val logo: String)
data class Teams(val home: Home, val away: Away)

data class Goals(val home: Int?, val away: Int?)

data class ResponseFixture(val fixture: Fixture, val teams: Teams, val goals: Goals)

data class FixtureModel(val response: List<ResponseFixture>)