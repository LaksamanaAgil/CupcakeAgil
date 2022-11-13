package com.example.cupcake.model
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.Locale
import java.text.SimpleDateFormat
import java.util.Calendar
import androidx.lifecycle.Transformations
import java.text.NumberFormat

private const val PRICE_PER_CUPCAKE = 2.00 //TODO: Harga untuk 1 cupcake
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00 //TODO: HARGA Cupcake untuk pesanan pickup hari ini

class OrderViewModel : ViewModel() {
    private fun updatePrice() { //TODO: method untuk mengupdate total harga
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
        // If the user selected the first option (today) for pickup, add the surcharge
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }
    private val _quantity = MutableLiveData<Int>() //TODO: METHOD ISIAN UNTUK JUMLAH CUPCAKE
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>() // TODO: METHOD ISIAN UNTUK RASA
    val flavor: LiveData<String> = _flavor

    private val _date = MutableLiveData<String>() //TODO: METHOD ISIAN UNTUK TANGGAL PESANAN
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>() //TODO:METHOD ISIAN UNTUK HARGA
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    private fun getPickupOptions(): List<String> { //TODO: METHOD UNTUK MENUNJUKKAN TANGGAL
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // Create a list of dates starting with the current date and the following 3 dates
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    fun setQuantity(numberCupcakes: Int) { //TODO: JUMLAH CUPCAKE
        _quantity.value = numberCupcakes
        updatePrice() // TODO: UPDATE HARGA
    }

    fun setFlavor(desiredFlavor: String) { // TODO: RASA YANG DIPILIH
        _flavor.value = desiredFlavor
    }

    fun setDate(pickupDate: String) { //TODO: TANGGAL PICKUP
        _date.value = pickupDate
        updatePrice()
    }
    fun hasNoFlavorSet(): Boolean { //TODO: MENGECEK APAKAH FLAVOR SUDAH DISET
        return _flavor.value.isNullOrEmpty()
    }
    fun resetOrder() { //TODO: FUNGSI RESET PESANAN
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }
    init {
        resetOrder() //TODO: Untuk mereset pesanan
    }
    val dateOptions = getPickupOptions() //TODO: UNTUK MENDAPAT PILIHAN TANGGAL PICKUP


}