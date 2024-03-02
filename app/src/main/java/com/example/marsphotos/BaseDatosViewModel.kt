import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.DataBase.DaoSicenet
import com.example.marsphotos.DataBase.Item
import com.example.marsphotos.ServicioWeb
import kotlinx.coroutines.launch

@Suppress("UNREACHABLE_CODE")
class BaseDatosViewModel(private val daoSicenet: DaoSicenet, private val servicioWeb: ServicioWeb) : ViewModel() {
    fun addNewItem(itemMatricula: String, itemNombre: String, itemCarrera: String, itemPromedio: String, itemSemestre: String) {
        val newItem = getNewItemEntry(itemMatricula, itemNombre, itemCarrera, itemPromedio, itemSemestre)
        insertItem(newItem)
    }

    private fun insertItem(item: Item.Item0) {
        viewModelScope.launch {
            daoSicenet.insertDatosdelalumno(item)
        }
    }

    private fun getNewItemEntry(itemMatricula: String, itemNombre: String, itemCarrera: String, itemPromedio: String, itemSemestre: String): Item.Item0 {
        return Item.Item0(
            itemMatricula = itemMatricula,
            itemNombre = itemNombre,
            itemCarrera = itemCarrera,
            itemPromedio = itemPromedio.toDouble(),
            itemSemestre = itemSemestre.toInt()
        )
    }

    fun descargarDatosDelWebService() {
        viewModelScope.launch {
            try {
                val datos = servicioWeb.obtenerDatos().body() ?: return@launch
                for (dato in datos) {
                    val newItem = getNewItemEntry(dato.itemMatricula, dato.itemNombre, dato.itemCarrera,
                        dato.itemPromedio.toString(), dato.itemSemestre.toString())
                    insertItem(newItem)
                }
            } catch (e: Exception) {
                // Manejar el error de alguna manera
            }
        }
    }
}
