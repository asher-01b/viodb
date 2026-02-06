<script setup>
import { ref } from "vue";

const form = ref({
  host: "localhost",
  port: "3306",
  username: "root",
  password: "",
});

const isLoading = ref(false);

const handleConnect = () => {
  isLoading.value = true;
  console.log("Connecting...", form.value);

  setTimeout(() => {
    isLoading.value = false;
    alert("Connect button clicked");
  }, 1000); // waiting 1s for test
};
</script>

<template>
  <div class="login-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <!-- use icons -->
          <el-icon size="24" color="#8A2BE2"><Connection /></el-icon>
          <span style="margin-left: 10px; font-weight: bold"
            >Create new connection (MySQL)</span
          >
        </div>
      </template>

      <el-form :model="form" label-width="70px">
        <el-form-item label="Host">
          <el-input v-model="form.host" placeholder="127.0.0.1" />
        </el-form-item>

        <el-form-item label="Port">
          <el-input v-model="form.port" placeholder="3306" />
        </el-form-item>

        <el-form-item label="User">
          <el-input v-model="form.username" placeholder="root" />
        </el-form-item>

        <el-form-item label="Password">
          <el-input v-model="form.password" type="password" show-password />
        </el-form-item>

        <el-form-item>
          <!-- type="primary" which is Element Plus's blue -->
          <!-- :loading spinning while waiting -->
          <el-button
            type="primary"
            @click="handleConnect"
            :loading="isLoading"
            style="
              width: 100%;
              background-color: #8a2be2;
              border-color: #8a2be2;
            "
          >
            Connection
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped>
/* center layout */
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f0f2f5;
}

.box-card {
  width: 400px;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
