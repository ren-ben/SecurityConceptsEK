<template>
  <div class="page">
    <div class="content-card">
      <div class="header">
        <div>
          <h1>Community</h1>
          <p class="description">Collaborate and share information</p>
        </div>
        <div class="status-badge authenticated">
          <svg class="status-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <path d="M9 11l3 3L22 4" stroke-width="2"/>
            <path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11" stroke-width="2"/>
          </svg>
          <span>Authenticated</span>
        </div>
      </div>

      <div class="welcome-banner">
        <div class="banner-content">
          <h2>Welcome, {{ authStore.user?.name }}</h2>
          <p>Access community resources and collaborate with other members</p>
        </div>
      </div>

      <div class="features-grid">
        <div class="feature-card">
          <div class="feature-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <line x1="12" y1="5" x2="12" y2="19" stroke-width="2"/>
              <line x1="5" y1="12" x2="19" y2="12" stroke-width="2"/>
            </svg>
          </div>
          <h3>Dashboard</h3>
          <p>View real-time windpark metrics and analytics</p>
          <span class="status active">Active</span>
        </div>

        <div class="feature-card">
          <div class="feature-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z" stroke-width="2"/>
            </svg>
          </div>
          <h3>Discussions</h3>
          <p>Participate in community forums and discussions</p>
          <span class="status active">Active</span>
        </div>

        <div class="feature-card">
          <div class="feature-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20" stroke-width="2"/>
              <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z" stroke-width="2"/>
            </svg>
          </div>
          <h3>Resources</h3>
          <p>Access documentation and knowledge base</p>
          <span class="status active">Active</span>
        </div>

        <div class="feature-card">
          <div class="feature-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z" stroke-width="2"/>
              <polyline points="14 2 14 8 20 8" stroke-width="2"/>
              <line x1="16" y1="13" x2="8" y2="13" stroke-width="2"/>
              <line x1="16" y1="17" x2="8" y2="17" stroke-width="2"/>
              <polyline points="10 9 9 9 8 9" stroke-width="2"/>
            </svg>
          </div>
          <h3>Reports</h3>
          <p>Generate and view community reports</p>
          <span class="status active">Active</span>
        </div>
      </div>

      <div class="api-section">
        <div class="section-header">
          <h3>Protected API Data</h3>
          <code class="endpoint">GET /api/user/data</code>
        </div>
        
        <div v-if="loading" class="loading-state">
          <div class="spinner"></div>
          <span>Loading user data...</span>
        </div>
        
        <div v-else-if="error" class="error-state">
          <svg class="error-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <circle cx="12" cy="12" r="10" stroke-width="2"/>
            <line x1="15" y1="9" x2="9" y2="15" stroke-width="2"/>
            <line x1="9" y1="9" x2="15" y2="15" stroke-width="2"/>
          </svg>
          <span>{{ error }}</span>
        </div>
        
        <pre v-else class="api-response">{{ JSON.stringify(userData, null, 2) }}</pre>
      </div>

      <div class="permissions-section">
        <h3>Your Permissions</h3>
        <div class="permissions-list">
          <div 
            v-for="role in authStore.user?.authorities" 
            :key="role" 
            class="permission-item"
          >
            <svg class="check-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <polyline points="20 6 9 17 4 12" stroke-width="2"/>
            </svg>
            <span>{{ role }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import api from '@/utils/api'

const authStore = useAuthStore()
const userData = ref(null)
const loading = ref(true)
const error = ref<string | null>(null)

onMounted(async () => {
  try {
    const response = await api.get('/api/user/data')
    userData.value = response.data
  } catch (err: any) {
    error.value = err.response?.data?.message || 'Failed to load user data'
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.page {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
}

.content-card {
  background: var(--nord1);
  border: 1px solid var(--nord2);
  border-radius: 12px;
  padding: 2.5rem;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 2rem;
  padding-bottom: 2rem;
  border-bottom: 1px solid var(--nord2);
  flex-wrap: wrap;
  gap: 1rem;
}

.header h1 {
  font-size: 2rem;
  font-weight: 700;
  color: var(--nord6);
  margin-bottom: 0.5rem;
  letter-spacing: -0.02em;
}

.description {
  color: var(--nord4);
  font-size: 0.95rem;
}

.status-badge {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  font-size: 0.85rem;
  font-weight: 500;
}

.status-badge.authenticated {
  background: var(--nord8);
  color: var(--nord0);
}

.status-icon {
  width: 18px;
  height: 18px;
  stroke-width: 2;
}

.welcome-banner {
  background: linear-gradient(135deg, var(--nord10) 0%, var(--nord9) 100%);
  border-radius: 8px;
  padding: 2rem;
  margin-bottom: 2rem;
}

.banner-content h2 {
  font-size: 1.5rem;
  font-weight: 600;
  color: white;
  margin-bottom: 0.5rem;
}

.banner-content p {
  color: rgba(255, 255, 255, 0.9);
  font-size: 0.95rem;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1rem;
  margin-bottom: 2rem;
}

.feature-card {
  background: var(--nord0);
  border: 1px solid var(--nord2);
  border-radius: 8px;
  padding: 1.5rem;
  transition: all 0.2s;
}

.feature-card:hover {
  border-color: var(--nord8);
  transform: translateY(-2px);
}

.feature-icon {
  width: 40px;
  height: 40px;
  background: var(--nord2);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 1rem;
}

.feature-icon svg {
  width: 22px;
  height: 22px;
  color: var(--nord8);
  stroke-width: 2;
}

.feature-card h3 {
  font-size: 1.05rem;
  font-weight: 600;
  color: var(--nord6);
  margin-bottom: 0.5rem;
}

.feature-card p {
  color: var(--nord4);
  font-size: 0.9rem;
  line-height: 1.6;
  margin-bottom: 0.75rem;
}

.status {
  display: inline-block;
  padding: 0.3rem 0.7rem;
  border-radius: 4px;
  font-size: 0.75rem;
  font-weight: 600;
}

.status.active {
  background: rgba(163, 190, 140, 0.2);
  color: var(--nord14);
}

.api-section {
  background: var(--nord0);
  border: 1px solid var(--nord2);
  border-radius: 8px;
  padding: 1.5rem;
  margin-bottom: 2rem;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
  flex-wrap: wrap;
  gap: 0.5rem;
}

.section-header h3 {
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--nord6);
}

.endpoint {
  background: var(--nord2);
  color: var(--nord8);
  padding: 0.4rem 0.8rem;
  border-radius: 4px;
  font-size: 0.85rem;
  font-family: 'Courier New', monospace;
}

.loading-state {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
  padding: 2rem;
  color: var(--nord4);
}

.spinner {
  width: 20px;
  height: 20px;
  border: 2px solid var(--nord3);
  border-top-color: var(--nord8);
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.error-state {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 1rem;
  background: rgba(191, 97, 106, 0.1);
  border: 1px solid var(--nord11);
  border-radius: 6px;
  color: var(--nord11);
}

.error-icon {
  width: 20px;
  height: 20px;
  flex-shrink: 0;
}

.api-response {
  background: var(--nord0);
  border: 1px solid var(--nord3);
  color: var(--nord14);
  padding: 1.5rem;
  border-radius: 6px;
  overflow-x: auto;
  font-family: 'Courier New', monospace;
  font-size: 0.9rem;
  line-height: 1.6;
  margin: 0;
}

.permissions-section {
  background: var(--nord0);
  border: 1px solid var(--nord2);
  border-radius: 8px;
  padding: 1.5rem;
}

.permissions-section h3 {
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--nord6);
  margin-bottom: 1rem;
}

.permissions-list {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.permission-item {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.75rem;
  background: var(--nord2);
  border-radius: 6px;
  color: var(--nord4);
  font-size: 0.9rem;
  font-weight: 500;
}

.check-icon {
  width: 18px;
  height: 18px;
  color: var(--nord14);
  stroke-width: 2;
  flex-shrink: 0;
}
</style>
