import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kaihatsu00.R

data class User(val id: Int, val name: String, val age: Int)

class UserAdapter(private val userList: List<User>, private val onDeleteClick: (User) -> Unit) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUserInfo: TextView = view.findViewById(R.id.tvUserInfo)
        val btnDeleteUser: Button = view.findViewById(R.id.btnDeleteUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.tvUserInfo.text = "ID: ${user.id}, Name: ${user.name}, Age: ${user.age}"

        holder.btnDeleteUser.setOnClickListener {
            onDeleteClick(user)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
