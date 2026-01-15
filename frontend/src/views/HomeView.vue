<template>
  <div class="home">
    <div class="hero-section">
      <div class="hero-content">
        <h1 class="title">Windpark Portal</h1>
        <p class="subtitle">Secure Authentication & Operations Management</p>

        <!-- AUTH SECTION (Not Logged In) -->
        <div v-if="!authStore.isAuthenticated" class="auth-container">
          
          <!-- Auth Method Tabs -->
          <div class="auth-tabs">
            <button 
              @click="authMode = 'ad'" 
              :class="['tab-btn', { active: authMode === 'ad' }]"
            >
              TGM Login
            </button>
            <button 
              @click="authMode = 'social'" 
              :class="['tab-btn', { active: authMode === 'social' }]"
            >
              Social Login
            </button>
          </div>

          <!-- OPTION A: TGM / AD Login Form -->
          <div v-if="authMode === 'ad'" class="login-form-container">
            <form @submit.prevent="handleAdLogin">
              <div class="form-group">
                <label for="username">Username</label>
                <div class="input-wrapper">
                  <svg class="input-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" stroke-width="2"/>
                    <circle cx="12" cy="7" r="4" stroke-width="2"/>
                  </svg>
                  <input 
                    id="username"
                    v-model="username" 
                    type="text" 
                    placeholder="e.g. mmueller"
                    class="input-field"
                    required
                  />
                </div>
              </div>

              <div class="form-group">
                <label for="password">Password</label>
                <div class="input-wrapper">
                  <svg class="input-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                    <rect x="3" y="11" width="18" height="11" rx="2" ry="2" stroke-width="2"/>
                    <path d="M7 11V7a5 5 0 0 1 10 0v4" stroke-width="2"/>
                  </svg>
                  <input 
                    id="password"
                    v-model="password" 
                    type="password" 
                    placeholder="••••••••"
                    class="input-field"
                    required
                  />
                </div>
              </div>
              
              <div v-if="authStore.error" class="error-banner">
                <svg class="error-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <circle cx="12" cy="12" r="10" stroke-width="2"/>
                  <line x1="12" y1="8" x2="12" y2="12" stroke-width="2"/>
                  <line x1="12" y1="16" x2="12.01" y2="16" stroke-width="2"/>
                </svg>
                <span>{{ authStore.error }}</span>
              </div>

              <button type="submit" class="submit-btn" :disabled="authStore.loading">
                <span v-if="!authStore.loading">Sign in</span>
                <span v-else class="spinner"></span>
              </button>
            </form>
          </div>

          <!-- OPTION B: Social Login Buttons -->
          <div v-else class="social-login-container">
            <p class="auth-description">Authenticate via external provider</p>
            <div class="auth-buttons">
              <button @click="authStore.loginWithGoogle()" class="social-btn">
                <svg class="social-icon" viewBox="0 0 24 24">
                  <path fill="currentColor" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
                  <path fill="currentColor" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
                  <path fill="currentColor" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
                  <path fill="currentColor" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
                </svg>
                Google
              </button>

              <button @click="authStore.loginWithGitHub()" class="social-btn">
                <svg class="social-icon" viewBox="0 0 24 24" fill="currentColor">
                  <path d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z"/>
                </svg>
                GitHub
              </button>
            </div>
          </div>
        </div>

        <!-- DASHBOARD (Logged In) -->
        <div v-else class="dashboard">
          <div class="welcome-card">
            <div class="welcome-header">
              <h2>Welcome back</h2>
              <span class="user-email">{{ authStore.user?.email || authStore.user?.name }}</span>
            </div>

            <div class="roles-section">
              <span class="roles-label">Active roles</span>
              <div class="roles-list">
                <span v-for="role in authStore.user?.authorities" :key="role" class="role-badge">
                  {{ role.replace('ROLE_', '') }}
                </span>
              </div>
            </div>
          </div>

          <div class="features-grid">
            <RouterLink to="/community" class="feature-card">
              <div class="card-header">
                <h3>Community</h3>
                <svg class="card-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" stroke-width="2"/>
                  <circle cx="9" cy="7" r="4" stroke-width="2"/>
                  <path d="M23 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75" stroke-width="2"/>
                </svg>
              </div>
              <p>Collaborate and share information</p>
            </RouterLink>

            <RouterLink v-if="authStore.isOps" to="/operations" class="feature-card highlight">
              <div class="card-header">
                <h3>Operations</h3>
                <svg class="card-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <circle cx="12" cy="12" r="3" stroke-width="2"/>
                  <path d="M12 1v6m0 6v6M1 12h6m6 0h6" stroke-width="2"/>
                  <path d="M4.93 4.93l4.24 4.24m5.66 0l4.24-4.24M19.07 19.07l-4.24-4.24m-5.66 0l-4.24 4.24" stroke-width="2"/>
                </svg>
              </div>
              <p>Control center access</p>
              <span class="badge">Restricted</span>
            </RouterLink>

            <RouterLink to="/public" class="feature-card">
              <div class="card-header">
                <h3>Public</h3>
                <svg class="card-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <circle cx="12" cy="12" r="10" stroke-width="2"/>
                  <path d="M2 12h20M12 2a15.3 15.3 0 0 1 4 10 15.3 15.3 0 0 1-4 10 15.3 15.3 0 0 1-4-10 15.3 15.3 0 0 1 4-10z" stroke-width="2"/>
                </svg>
              </div>
              <p>General information</p>
            </RouterLink>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const authMode = ref<'ad' | 'social'>('ad')
const username = ref('')
const password = ref('')

const handleAdLogin = async () => {
  try {
    await authStore.loginWithAd(username.value, password.value)
  } catch (e) {
    // Error is handled in store and displayed in template
  }
}
</script>

<style scoped>
.home {
  min-height: calc(100vh - 120px);
  display: flex;
  align-items: center;
  justify-content: center;
}

.hero-section {
  width: 100%;
  max-width: 900px;
}

.hero-content {
  background: var(--nord1);
  border-radius: 12px;
  border: 1px solid var(--nord2);
  padding: 3rem;
}

.title {
  font-size: 2.5rem;
  font-weight: 700;
  color: var(--nord6);
  margin-bottom: 0.5rem;
  letter-spacing: -0.02em;
}

.subtitle {
  font-size: 1.1rem;
  color: var(--nord4);
  margin-bottom: 3rem;
  font-weight: 400;
}

/* Auth Container Styling */
.auth-container {
  background: var(--nord0);
  border: 1px solid var(--nord2);
  border-radius: 8px;
  padding: 2rem;
  max-width: 450px;
    margin: 0 auto;

}

.auth-tabs {
  display: flex;
  background: var(--nord1);
  padding: 0.25rem;
  border-radius: 8px;
  margin-bottom: 1.5rem;
  border: 1px solid var(--nord2);
}

.tab-btn {
  flex: 1;
  background: transparent;
  border: none;
  padding: 0.6rem;
  border-radius: 6px;
  color: var(--nord4);
  font-size: 0.9rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.tab-btn:hover {
  color: var(--nord6);
}

.tab-btn.active {
  background: var(--nord3);
  color: var(--nord6);
  font-weight: 600;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

/* Form Styling */
.login-form-container {
  animation: fadeIn 0.3s ease;
}

.form-group {
  margin-bottom: 1.25rem;
}

.form-group label {
  display: block;
  font-size: 0.85rem;
  color: var(--nord4);
  margin-bottom: 0.5rem;
  font-weight: 500;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.input-icon {
  position: absolute;
  left: 12px;
  width: 18px;
  height: 18px;
  color: var(--nord3);
  pointer-events: none;
  transition: color 0.2s;
}

.input-field {
  width: 100%;
  padding: 0.75rem 1rem 0.75rem 2.5rem;
  background: var(--nord0);
  border: 1px solid var(--nord3);
  border-radius: 6px;
  color: var(--nord6);
  font-size: 0.95rem;
  font-family: inherit;
  transition: all 0.2s;
}

.input-field:focus {
  outline: none;
  border-color: var(--nord8);
  box-shadow: 0 0 0 2px rgba(136, 192, 208, 0.1);
}

.input-field:focus + .input-icon,
.input-wrapper:focus-within .input-icon {
  color: var(--nord8);
}

.submit-btn {
  width: 100%;
  padding: 0.75rem;
  background: var(--nord10);
  color: var(--nord6);
  border: none;
  border-radius: 6px;
  font-size: 0.95rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s;
  display: flex;
  justify-content: center;
  align-items: center;
}

.submit-btn:hover {
  background: var(--nord9);
}

.submit-btn:disabled {
  background: var(--nord3);
  cursor: not-allowed;
  opacity: 0.7;
}

/* Error Banner */
.error-banner {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  background: rgba(191, 97, 106, 0.15);
  border: 1px solid var(--nord11);
  color: var(--nord11);
  padding: 0.75rem;
  border-radius: 6px;
  margin-bottom: 1.25rem;
  font-size: 0.9rem;
}

.error-icon {
  width: 18px;
  height: 18px;
  flex-shrink: 0;
}

/* Social Buttons */
.auth-description {
  color: var(--nord4);
  font-size: 0.95rem;
  margin-bottom: 1.5rem;
}

.auth-buttons {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.social-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.75rem;
  padding: 0.75rem;
  background: var(--nord1);
  border: 1px solid var(--nord3);
  border-radius: 6px;
  color: var(--nord4);
  font-size: 0.95rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.social-btn:hover {
  background: var(--nord2);
  border-color: var(--nord8);
  color: var(--nord6);
}

.social-icon {
  width: 20px;
  height: 20px;
}

/* Spinner */
.spinner {
  width: 18px;
  height: 18px;
  border: 2px solid rgba(255,255,255,0.3);
  border-top-color: #fff;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(5px); }
  to { opacity: 1; transform: translateY(0); }
}

/* Dashboard Styles (Same as before) */
.dashboard {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.welcome-card {
  background: var(--nord0);
  border: 1px solid var(--nord2);
  border-radius: 8px;
  padding: 2rem;
}

.welcome-header h2 {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--nord6);
  margin-bottom: 0.5rem;
}

.user-email {
  color: var(--nord8);
  font-size: 1rem;
  font-weight: 500;
}

.roles-section {
  margin-top: 1.5rem;
  padding-top: 1.5rem;
  border-top: 1px solid var(--nord2);
}

.roles-label {
  font-size: 0.85rem;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  color: var(--nord3);
  font-weight: 600;
}

.roles-list {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-top: 0.75rem;
}

.role-badge {
  background: var(--nord3);
  color: var(--nord6);
  padding: 0.4rem 0.9rem;
  border-radius: 6px;
  font-size: 0.85rem;
  font-weight: 500;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1rem;
}

.feature-card {
  background: var(--nord0);
  border: 1px solid var(--nord2);
  border-radius: 8px;
  padding: 1.5rem;
  text-decoration: none;
  color: var(--nord4);
  transition: all 0.2s;
  position: relative;
}

.feature-card:hover {
  border-color: var(--nord8);
  transform: translateY(-2px);
}

.feature-card.highlight {
  border-color: var(--nord11);
}

.feature-card.highlight:hover {
  border-color: var(--nord12);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.75rem;
}

.card-header h3 {
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--nord6);
}

.card-icon {
  width: 24px;
  height: 24px;
  color: var(--nord8);
  stroke-width: 2;
}

.feature-card p {
  color: var(--nord4);
  font-size: 0.9rem;
  line-height: 1.5;
}

.badge {
  display: inline-block;
  margin-top: 1rem;
  padding: 0.3rem 0.7rem;
  background: var(--nord11);
  color: var(--nord0);
  border-radius: 4px;
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}
</style>
