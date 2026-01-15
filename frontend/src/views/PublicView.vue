<template>
  <div class="page">
    <div class="content-card">
      <div class="header">
        <div>
          <h1>Public Information</h1>
          <p class="description">Accessible without authentication</p>
        </div>
        <div class="status-badge public">
          <svg class="status-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <circle cx="12" cy="12" r="10" stroke-width="2"/>
            <path d="M12 6v6l4 2" stroke-width="2"/>
          </svg>
          <span>Public</span>
        </div>
      </div>

      <div class="content-section">
        <h2>About This Portal</h2>
        <p>This windpark management portal provides secure access to operational data and community resources. Authentication is required for most features.</p>
      </div>

      <div class="api-section">
        <div class="section-header">
          <h3>API Response</h3>
          <code class="endpoint">GET /api/public/info</code>
        </div>
        
        <div v-if="loading" class="loading-state">
          <div class="spinner"></div>
          <span>Loading...</span>
        </div>
        
        <div v-else-if="error" class="error-state">
          <svg class="error-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <circle cx="12" cy="12" r="10" stroke-width="2"/>
            <line x1="12" y1="8" x2="12" y2="12" stroke-width="2"/>
            <line x1="12" y1="16" x2="12.01" y2="16" stroke-width="2"/>
          </svg>
          <span>{{ error }}</span>
        </div>
        
        <pre v-else class="api-response">{{ JSON.stringify(publicData, null, 2) }}</pre>
      </div>

      <div class="info-grid">
        <div class="info-card">
          <div class="info-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <rect x="3" y="3" width="18" height="18" rx="2" stroke-width="2"/>
              <line x1="9" y1="3" x2="9" y2="21" stroke-width="2"/>
            </svg>
          </div>
          <h4>Documentation</h4>
          <p>Access technical documentation and API reference</p>
        </div>

        <div class="info-card">
          <div class="info-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z" stroke-width="2"/>
            </svg>
          </div>
          <h4>Support</h4>
          <p>Get help from our support team</p>
        </div>

        <div class="info-card">
          <div class="info-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <circle cx="12" cy="12" r="10" stroke-width="2"/>
              <path d="M12 16v-4M12 8h.01" stroke-width="2"/>
            </svg>
          </div>
          <h4>Status</h4>
          <p>Check system status and uptime</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import api from '@/utils/api'

const publicData = ref(null)
const loading = ref(true)
const error = ref<string | null>(null)

onMounted(async () => {
  try {
    const response = await api.get('/api/public/info')
    publicData.value = response.data
  } catch (err: any) {
    error.value = err.response?.data?.message || 'Failed to load data'
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

.status-badge.public {
  background: var(--nord14);
  color: var(--nord0);
}

.status-icon {
  width: 18px;
  height: 18px;
  stroke-width: 2;
}

.content-section {
  margin-bottom: 2rem;
}

.content-section h2 {
  font-size: 1.3rem;
  font-weight: 600;
  color: var(--nord6);
  margin-bottom: 1rem;
}

.content-section p {
  color: var(--nord4);
  line-height: 1.7;
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

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 1rem;
}

.info-card {
  background: var(--nord0);
  border: 1px solid var(--nord2);
  border-radius: 8px;
  padding: 1.5rem;
  transition: all 0.2s;
}

.info-card:hover {
  border-color: var(--nord8);
}

.info-icon {
  width: 40px;
  height: 40px;
  background: var(--nord2);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 1rem;
}

.info-icon svg {
  width: 22px;
  height: 22px;
  color: var(--nord8);
  stroke-width: 2;
}

.info-card h4 {
  font-size: 1.05rem;
  font-weight: 600;
  color: var(--nord6);
  margin-bottom: 0.5rem;
}

.info-card p {
  color: var(--nord4);
  font-size: 0.9rem;
  line-height: 1.6;
}
</style>
