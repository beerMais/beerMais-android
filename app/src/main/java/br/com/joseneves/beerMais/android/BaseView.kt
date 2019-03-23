package br.com.joseneves.beerMais.android

interface BaseView<T> {
    fun setPresenter(presenter: T)
}