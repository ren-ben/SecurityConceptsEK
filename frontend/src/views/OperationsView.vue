<template>
  <div class="page">
    <div class="content-card">
      <div class="header">
        <div>
          <h1>Operations Control Center</h1>
          <p class="description">Restricted area for operations personnel</p>
        </div>
        <div class="status-badge restricted">
          <svg class="status-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <rect x="3" y="11" width="18" height="11" rx="2" ry="2" stroke-width="2"/>
            <path d="M7 11V7a5 5 0 0 1 10 0v4" stroke-width="2"/>
          </svg>
          <span>Restricted</span>
        </div>
      </div>

      <div class="alert-banner">
        <svg class="alert-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z" stroke-width="2"/>
          <line x1="12" y1="9" x2="12" y2="13" stroke-width="2"/>
          <line x1="12" y1="17" x2="12.01" y2="17" stroke-width="2"/>
        </svg>
        <div>
          <h3>Restricted Access Area</h3>
          <p>This section requires ROLE_OPS authorization. All activities are monitored and logged.</p>
        </div>
      </div>

      <div class="ops-grid">
        <div class="ops-card">
          <div class="ops-header">
            <div class="ops-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <circle cx="12" cy="12" r="10" stroke-width="2"/>
                <line x1="2" y1="12" x2="22" y2="12" stroke-width="2"/>
                <path d="M12 2a15.3 15.3 0 0 1 4 10 15.3 15.3 0 0 1-4 10 15.3 15.3 0 0 1-4-10 15.3 15.3 0 0 1 4-10z" stroke-width="2"/>
              </svg>
            </div>
            <span class="status-dot online"></span>
          </div>
          <h3>Power Distribution</h3>
          <p>Monitor and control energy flow across the grid</p>
          <div class="metric">
            <span class="metric-label">Status</span>
            <span class="metric-value online">Online</span>
          </div>
        </div>

        <div class="ops-card">
          <div class="ops-header">
            <div class="ops-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z" stroke-width="2"/>
              </svg>
            </div>
            <span class="status-dot online"></span>
          </div>
          <h3>Maintenance</h3>
          <p>Schedule and track maintenance operations</p>
          <div class="metric">
            <span class="metric-label">Tasks</span>
            <span class="metric-value">3 pending</span>
          </div>
        </div>

        <div class="ops-card">
          <div class="ops-header">
            <div class="ops-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <polyline points="22 12 18 12 15 21 9 3 6 12 2 12" stroke-width="2"/>
              </svg>
            </div>
            <span class="status-dot online"></span>
          </div>
          <h3>Monitoring</h3>
          <p>Real-time sensor data and system alerts</p>
          <div class="metric">
            <span class="metric-label">Sensors</span>
            <span class="metric-value online">24 active</span>
          </div>
        </div>

        <div class="ops-card">
          <div class="ops-header">
            <div class="ops-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z" stroke-width="2"/>
                <polyline points="14 2 14 8 20 8" stroke-width="2"/>
                <line x1="16" y1="13" x2="8" y2="13" stroke-width="2"/>
                <line x1="16" y1="17" x2="8" y2="17" stroke-width="2"/>
              </svg>
            </div>
            <span class="status-dot online"></span>
          </div>
          <h3>Audit Logs</h3>
          <p>Access operational logs and reports</p>
          <div class="metric">
            <span class="metric-label">Entries</span>
            <span class="metric-value">1,247</span>
          </div>
        </div>
      </div>

      <div class="api-section">
        <div class="section-header">
          <h3>Secured Operations Data</h3>
          <code class="endpoint">GET /api/ops/data</code>
        </div>
        
        <div v-if="loading" class="loading-state">
          <div class="spinner"></div>
          <span>Loading operational data...</span>
        </div>
        
        <div v-else-if="error" class="error-state">
          <svg class="error-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <circle cx="12" cy="12" r="10" stroke-width="2"/>
            <line x1="12" y1="8" x2="12" y2="12" stroke-width="2"/>
            <line x1="12" y1="16" x2="12.01" y2="16" stroke-width="2"/>
          </svg>
          <div>
            <strong>Access Denied</strong>
            <p>{{ error }}</p>
          </div>
        </div>
        
        <div v-else class="success-state">
          <svg class="success-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14" stroke-width="2"/>
            <polyline points="22 4 12 14.01 9 11.01" stroke-width="2"/>
          </svg>
          <div class="success-content">
            <strong>Access Granted</strong>
            <pre class="api-response">{{ JSON.stringify(opsData, null, 2) }}</pre>
          </div>
        </div>
      </div>

      <div class="permissions-section">
        <h3>Active Authorization</h3>
        <div class="permissions-grid">
          <div 
            v-for="role in authStore.user?.authorities" 
            :key="role" 
            class="permission-card"
          >
            <svg class="permission-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z" stroke-width="2"/>
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
const opsData = ref(null)
const loading = ref(true)
const error = ref<string | null>(null)

onMounted(async () => {
  try {
    const response = await api.get('/api/ops/data')
    opsData.value = response.data
  } catch (err: any) {
    if (err.response?.status === 403) {
      error.value = 'You do not have the required OPS role permissions to access this data'
    } else {
      error.value = err.response?.data?.message || 'Failed to load operational data'
    }
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

.status-badge.restricted {
  background: var(--nord11);
  color: white;
}

.status-icon {
  width: 18px;
  height: 18px;
  stroke-width: 2;
}

.alert-banner {
  background: rgba(235, 203, 139, 0.1);
  border: 1px solid var(--nord13);
  border-radius: 8px;
  padding: 1.5rem;
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
}

.alert-icon {
  width: 24px;
  height: 24px;
  color: var(--nord13);
  flex-shrink: 0;
  stroke-width: 2;
}

.alert-banner h3 {
  font-size: 1.05rem;
  font-weight: 600;
  color: var(--nord6);
  margin-bottom: 0.3rem;
}

.alert-banner p {
  color: var(--nord4);
  font-size: 0.9rem;
  line-height: 1.6;
}

.ops-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 1rem;
  margin-bottom: 2rem;
}

.ops-card {
  background: var(--nord0);
  border: 1px solid var(--nord2);
  border-radius: 8px;
  padding: 1.5rem;
  transition: all 0.2s;
}

.ops-card:hover {
  border-color: var(--nord11);
  transform: translateY(-2px);
}

.ops-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.ops-icon {
  width: 40px;
  height: 40px;
  background: var(--nord2);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.ops-icon svg {
  width: 22px;
  height: 22px;
  color: var(--nord11);
  stroke-width: 2;
}

.status-dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  animation: pulse 2s infinite;
}

.status-dot.online {
  background: var(--nord14);
  box-shadow: 0 0 0 0 var(--nord14);
}

@keyframes pulse {
  0% {
    box-shadow: 0 0 0 0 rgba(163, 190, 140, 0.7);
  }
  70% {
    box-shadow: 0 0 0 6px rgba(163, 190, 140, 0);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(163, 190, 140, 0);
  }
}

.ops-card h3 {
  font-size: 1.05rem;
  font-weight: 600;
  color: var(--nord6);
  margin-bottom: 0.5rem;
}

.ops-card p {
  color: var(--nord4);
  font-size: 0.9rem;
  line-height: 1.6;
  margin-bottom: 1rem;
}

.metric {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.75rem;
  background: var(--nord2);
  border-radius: 6px;
}

.metric-label {
  font-size: 0.85rem;
  color: var(--nord4);
  font-weight: 500;
}

.metric-value {
  font-size: 0.85rem;
  font-weight: 600;
  color: var(--nord6);
}

.metric-value.online {
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
  color: var(--nord11);
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
  border-top-color: var(--nord11);
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.error-state {
  display: flex;
  align-items: flex-start;
  gap: 1rem;
  padding: 1.5rem;
  background: rgba(191, 97, 106, 0.1);
  border: 1px solid var(--nord11);
  border-radius: 6px;
}

.error-icon {
  width: 24px;
  height: 24px;
  color: var(--nord11);
  flex-shrink: 0;
  stroke-width: 2;
}

.error-state strong {
  display: block;
  color: var(--nord11);
  font-weight: 600;
  margin-bottom: 0.3rem;
}

.error-state p {
  color: var(--nord4);
  font-size: 0.9rem;
}

.success-state {
  display: flex;
  align-items: flex-start;
  gap: 1rem;
}

.success-icon {
  width: 24px;
  height: 24px;
  color: var(--nord14);
  flex-shrink: 0;
  stroke-width: 2;
  margin-top: 0.3rem;
}

.success-content {
  flex: 1;
}

.success-content strong {
  display: block;
  color: var(--nord14);
  font-weight: 600;
  margin-bottom: 1rem;
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

.permissions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 0.75rem;
}

.permission-card {
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

.permission-icon {
  width: 20px;
  height: 20px;
  color: var(--nord14);
  stroke-width: 2;
  flex-shrink: 0;
}
</style>
