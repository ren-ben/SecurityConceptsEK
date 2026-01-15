<template>
  <div id="app">
    <nav class="navbar">
      <div class="nav-container">
        <RouterLink to="/" class="logo">
          <svg class="logo-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <path d="M13 2L3 14h9l-1 8 10-12h-9l1-8z" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          <span>Windpark Portal</span>
        </RouterLink>
        
        <div class="nav-links">
          <RouterLink to="/public" class="nav-link">Public</RouterLink>
          <RouterLink v-if="authStore.isAuthenticated" to="/community" class="nav-link">
            Community
          </RouterLink>
          <RouterLink v-if="authStore.isOps" to="/operations" class="nav-link">
            Operations
          </RouterLink>
          
          <div v-if="authStore.isAuthenticated" class="user-section">
            <div class="user-info">
              <div class="avatar">
                <img v-if="authStore.user?.picture" :src="authStore.user.picture" />
                <span v-else>{{ authStore.user?.name?.charAt(0) }}</span>
              </div>
              <span class="username">{{ authStore.user?.name }}</span>
            </div>
            <button @click="authStore.logout()" class="btn-logout">Sign out</button>
          </div>
        </div>
      </div>
    </nav>

    <main class="main-content">
      <RouterView />
    </main>

    <footer class="footer">
      <div class="footer-content">
        <span>© 2026 Windpark Portal</span>
        <span class="separator">•</span>
        <span>TGM Security Concepts</span>
      </div>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()

onMounted(() => {
  authStore.fetchUser()
})
</script>

<style>
:root {
  --nord0: #2E3440;
  --nord1: #3B4252;
  --nord2: #434C5E;
  --nord3: #4C566A;
  --nord4: #D8DEE9;
  --nord5: #E5E9F0;
  --nord6: #ECEFF4;
  --nord7: #8FBCBB;
  --nord8: #88C0D0;
  --nord9: #81A1C1;
  --nord10: #5E81AC;
  --nord11: #BF616A;
  --nord12: #D08770;
  --nord13: #EBCB8B;
  --nord14: #A3BE8C;
  --nord15: #B48EAD;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Inter', sans-serif;
  background: var(--nord0);
  color: var(--nord4);
  line-height: 1.6;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.navbar {
  background: var(--nord1);
  border-bottom: 1px solid var(--nord2);
  position: sticky;
  top: 0;
  z-index: 100;
  backdrop-filter: blur(10px);
}

.nav-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 2rem;
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  text-decoration: none;
  color: var(--nord4);
  font-weight: 600;
  font-size: 1rem;
  transition: color 0.2s;
}

.logo:hover {
  color: var(--nord8);
}

.logo-icon {
  width: 24px;
  height: 24px;
  color: var(--nord8);
}

.nav-links {
  display: flex;
  gap: 0.5rem;
  align-items: center;
}

.nav-link {
  text-decoration: none;
  color: var(--nord4);
  padding: 0.5rem 1rem;
  border-radius: 6px;
  font-size: 0.9rem;
  font-weight: 500;
  transition: all 0.2s;
}

.nav-link:hover {
  background: var(--nord2);
  color: var(--nord6);
}

.nav-link.router-link-active {
  background: var(--nord2);
  color: var(--nord8);
}

.user-section {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-left: 1rem;
  padding-left: 1rem;
  border-left: 1px solid var(--nord2);
}

.user-info {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: var(--nord3);
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  border: 2px solid var(--nord8);
  font-weight: 600;
  font-size: 0.85rem;
  color: var(--nord8);
}

.avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.username {
  font-size: 0.9rem;
  font-weight: 500;
  color: var(--nord4);
}

.btn-logout {
  background: transparent;
  color: var(--nord11);
  border: 1px solid var(--nord11);
  padding: 0.5rem 1rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.85rem;
  font-weight: 500;
  transition: all 0.2s;
}

.btn-logout:hover {
  background: var(--nord11);
  color: var(--nord0);
}

.main-content {
  flex: 1;
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
  padding: 2rem;
}

.footer {
  background: var(--nord1);
  border-top: 1px solid var(--nord2);
  padding: 1.5rem 2rem;
  margin-top: auto;
}

.footer-content {
  max-width: 1400px;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
  font-size: 0.85rem;
  color: var(--nord3);
}

.separator {
  color: var(--nord2);
}

@media (max-width: 768px) {
  .nav-container {
    flex-direction: column;
    height: auto;
    padding: 1rem;
    gap: 1rem;
  }

  .user-section {
    border-left: none;
    padding-left: 0;
    margin-left: 0;
  }
}
</style>
