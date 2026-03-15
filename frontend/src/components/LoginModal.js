import { createInput } from './Input.js';
import { createButton } from './Button.js';
import { api } from '../services/api.js';

export function createLoginModal({ onSuccess }) {
  const overlay = document.createElement('div');
  overlay.className = 'login-overlay';

  const modal = document.createElement('div');
  modal.className = 'login-modal';

  const header = document.createElement('div');
  header.className = 'login-header';
  header.innerHTML = `
    <h2>Welcome Back</h2>
    <p class="text-muted">Sign in to order your favorite food</p>
  `;

  const closeBtn = document.createElement('button');
  closeBtn.className = 'login-close';
  closeBtn.innerHTML = '&times;';
  closeBtn.onclick = () => overlay.remove();

  const form = document.createElement('form');
  form.className = 'login-form flex-col';
  
  const emailInput = createInput({ 
    label: 'Email', 
    type: 'email', 
    placeholder: 'owner1@test.com',
    value: 'owner1@test.com', // Pre-fill for ease of testing
    required: true 
  });
  
  const passwordInput = createInput({ 
    label: 'Password', 
    type: 'password', 
    placeholder: 'pass123',
    value: 'pass123', // Pre-fill for ease of testing
    required: true 
  });

  const errorMsg = document.createElement('div');
  errorMsg.className = 'login-error text-danger text-sm mb-md';
  errorMsg.style.display = 'none';

  const submitBtn = createButton({ text: 'Login', variant: 'primary', size: 'lg', className: 'w-full mt-sm' });

  form.appendChild(emailInput);
  form.appendChild(passwordInput);
  form.appendChild(errorMsg);
  form.appendChild(submitBtn);

  form.onsubmit = async (e) => {
    e.preventDefault();
    errorMsg.style.display = 'none';
    submitBtn.innerHTML = '<div class="loader" style="width:20px; height:20px; border-width:2px; margin:auto;"></div>';
    submitBtn.disabled = true;

    const email = emailInput.querySelector('input').value;
    const password = passwordInput.querySelector('input').value;

    try {
      await api.login(email, password);
      overlay.remove();
      if (onSuccess) onSuccess();
    } catch (err) {
      console.error(err);
      errorMsg.textContent = 'Invalid email or password.';
      errorMsg.style.display = 'block';
    } finally {
      submitBtn.textContent = 'Login';
      submitBtn.disabled = false;
    }
  };

  // Kakao OAuth stub
  const socialDivider = document.createElement('div');
  socialDivider.className = 'login-divider';
  socialDivider.innerHTML = '<span>or continue with</span>';

  const kakaoBtn = createButton({ text: 'Kakao', variant: 'outline', size: 'lg', className: 'w-full' });
  kakaoBtn.onclick = async () => {
    try {
      kakaoBtn.innerHTML = '<div class="loader" style="width:20px; height:20px; border-width:2px; margin:auto;"></div>';
      kakaoBtn.disabled = true;
      const res = await api.getKakaoLoginUrl();
      if (res && res.data) {
        window.location.href = res.data;
      }
    } catch (err) {
      console.error(err);
      kakaoBtn.textContent = 'Kakao';
      kakaoBtn.disabled = false;
      alert('Failed to initialize Kakao login');
    }
  };

  modal.appendChild(closeBtn);
  modal.appendChild(header);
  modal.appendChild(form);
  modal.appendChild(socialDivider);
  modal.appendChild(kakaoBtn);

  overlay.appendChild(modal);

  // Close on outside click
  overlay.addEventListener('click', (e) => {
    if (e.target === overlay) overlay.remove();
  });

  return overlay;
}
