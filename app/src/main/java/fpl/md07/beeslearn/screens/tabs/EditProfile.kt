package fpl.md07.beeslearn.screens.tabs

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fpl.md07.beeslearn.R
import fpl.md07.beeslearn.components.BackComponent
import fpl.md07.beeslearn.components.CustomTextField
import fpl.md07.beeslearn.models.UserModel
import fpl.md07.beeslearn.screens.NunitoBold
import fpl.md07.beeslearn.ui.theme.Nunito_Bold
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.viewModelFactory
import fpl.md07.beeslearn.GlobalVariable.UserSession
import fpl.md07.beeslearn.requests.UpdateUserRequest
import fpl.md07.beeslearn.viewmodels.EditProfileViewModel


@Composable
fun EditProfile(
    navController: NavController,
    editProfileViewModel: EditProfileViewModel = viewModel()
) {
    var userr = UserSession.currentUser
    Log.d("cde", userr.toString())
    var name by remember { mutableStateOf(userr?.username ?: "") }
    var email by remember { mutableStateOf(userr?.email ?: "") }
    var dateOfBirth by remember { mutableStateOf(userr?.dateOfBirth ?: "") }
    var phoneNumber by remember { mutableStateOf(userr?.phoneNumber ?: "") }

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BackComponent(navController)
            Text(
                "Edit Profile",
                fontSize = 18.sp,
                fontFamily = NunitoBold,
                color = colorResource(id = R.color.secondary_color),
                modifier = Modifier.padding(end = 30.dp)

            )
            Text("")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(horizontal = 40.dp, vertical = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.avatarsetting),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            "Change the profile image",
            fontSize = 10.sp,
            fontFamily = NunitoBold,
            color = Color(0xFF777777),
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )

        CustomTextField(
            labelText = "Name",
            value = name,
            onValueChange = { name = it },
            placeholder = "Beeslearn"
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomTextField(
            labelText = "Email",
            value = email,
            onValueChange = { email = it },
            placeholder = "Beeslearn@email.com",

            )

        Spacer(modifier = Modifier.height(20.dp))

        CustomTextField(
            labelText = "Date of birth",
            value = dateOfBirth,
            onValueChange = { dateOfBirth = it },
            placeholder = dateOfBirth
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomTextField(
            labelText = "Phone number",
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            placeholder = phoneNumber
        )


        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                "Đổi mật khẩu",
                color = Color(0xFF777777),
                fontFamily = NunitoBold,
                fontSize = 13.sp,
                modifier = Modifier.clickable {
                    navController.navigate("ChangePasswordScreen")
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                // Kiểm tra dữ liệu đầu vào
                if (name.isEmpty() || email.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val updatedUser = UpdateUserRequest(
                    email = email,
                    username = name,
                    phoneNumber = if (phoneNumber.isBlank()) null else phoneNumber,
                    dateOfBirth = if (dateOfBirth.isBlank()) null else dateOfBirth,
                    profileImageUrl = "" //không call
                )

                editProfileViewModel.updateProfile(
                    updatedUser = updatedUser,
                    onSuccess = { updatedUserModel ->
                        UserSession.currentUser = updatedUserModel
                        Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    },
                    onError = { errorMessage ->
                        // Hiển thị thông báo lỗi
                        Toast.makeText(context, "Lỗi: $errorMessage", Toast.LENGTH_LONG).show()
                        Log.e("EditProfile", "Lỗi cập nhật: $errorMessage")
                    }
                )
            },
            colors = ButtonDefaults.buttonColors(Color(0xFFFFD528)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "LƯU THAY ĐỔI",
                color = Color.White,
                fontFamily = Nunito_Bold
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun EditProfilePre(){
    val navController = rememberNavController()
    EditProfile(navController)
}


