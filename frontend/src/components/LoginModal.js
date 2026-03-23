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
    required: true 
  });

  const userAccountInput = createInput({ 
    label: 'User ID', 
    type: 'text', 
    placeholder: 'user123',
  });
  userAccountInput.style.display = 'none';

  const passwordInput = createInput({ 
    label: 'Password', 
    type: 'password', 
    placeholder: 'pass123',
    required: true 
  });

  const usernameInput = createInput({ 
    label: 'Name', 
    type: 'text', 
    placeholder: 'John Doe',
  });
  usernameInput.style.display = 'none';

  const phoneInput = createInput({ 
    label: 'Phone Number', 
    type: 'text', 
    placeholder: '010-1234-5678',
  });
  phoneInput.style.display = 'none';

  const errorMsg = document.createElement('div');
  errorMsg.className = 'login-error text-danger text-sm mb-md';
  errorMsg.style.display = 'none';

  const submitBtn = createButton({ text: 'Login', type: 'submit', variant: 'primary', size: 'lg', className: 'w-full mt-sm' });

  // Add toggle mode button
  const toggleModeContainer = document.createElement('div');
  toggleModeContainer.style.textAlign = 'center';
  toggleModeContainer.style.marginTop = '10px';
  const toggleModeBtn = document.createElement('button');
  toggleModeBtn.type = 'button';
  toggleModeBtn.textContent = 'Need an account? Sign up';
  toggleModeBtn.style.background = 'none';
  toggleModeBtn.style.border = 'none';
  toggleModeBtn.style.color = 'var(--primary, #007bff)';
  toggleModeBtn.style.cursor = 'pointer';
  toggleModeBtn.style.textDecoration = 'underline';
  
  let isSignupMode = false;
  
  toggleModeBtn.onclick = () => {
    isSignupMode = !isSignupMode;
    errorMsg.style.display = 'none';
    if(isSignupMode) {
      userAccountInput.style.display = 'block';
      userAccountInput.querySelector('input').required = true;
      usernameInput.style.display = 'block';
      usernameInput.querySelector('input').required = true;
      phoneInput.style.display = 'block';
      phoneInput.querySelector('input').required = true;

      emailInput.querySelector('input').value = '';
      passwordInput.querySelector('input').value = '';
      
      header.innerHTML = `
        <h2>Create Account</h2>
        <p class="text-muted">Join us to order your favorite food</p>
      `;
      submitBtn.textContent = 'Sign Up';
      toggleModeBtn.textContent = 'Already have an account? Login';
      socialDivider.style.display = 'none';
      kakaoBtn.style.display = 'none';
    } else {
      userAccountInput.style.display = 'none';
      userAccountInput.querySelector('input').required = false;
      usernameInput.style.display = 'none';
      usernameInput.querySelector('input').required = false;
      phoneInput.style.display = 'none';
      phoneInput.querySelector('input').required = false;

      emailInput.querySelector('input').value = '';
      passwordInput.querySelector('input').value = '';

      header.innerHTML = `
        <h2>Welcome Back</h2>
        <p class="text-muted">Sign in to order your favorite food</p>
      `;
      submitBtn.textContent = 'Login';
      toggleModeBtn.textContent = 'Need an account? Sign up';
      socialDivider.style.display = 'flex';
      kakaoBtn.style.display = 'block';
    }
  };
  toggleModeContainer.appendChild(toggleModeBtn);

  form.appendChild(emailInput);
  form.appendChild(userAccountInput);
  form.appendChild(passwordInput);
  form.appendChild(usernameInput);
  form.appendChild(phoneInput);
  form.appendChild(errorMsg);
  form.appendChild(submitBtn);
  form.appendChild(toggleModeContainer);

  form.onsubmit = async (e) => {
    e.preventDefault();
    errorMsg.style.display = 'none';
    submitBtn.innerHTML = '<div class="loader" style="width:20px; height:20px; border-width:2px; margin:auto;"></div>';
    submitBtn.disabled = true;

    const email = emailInput.querySelector('input').value;
    const password = passwordInput.querySelector('input').value;

    try {
      if (isSignupMode) {
        const userAccount = userAccountInput.querySelector('input').value;
        const username = usernameInput.querySelector('input').value;
        const phone = phoneInput.querySelector('input').value;
        
        await api.signup({
          email,
          userAccount,
          password,
          username,
          phoneNumber: phone
        });
        
        // 성공 시 팝업 없이 다시 로그인 모드로 전환
        toggleModeBtn.click();
      } else {
        await api.login(email, password);
        overlay.remove();
        if (onSuccess) onSuccess();
      }
    } catch (err) {
      console.error(err);
      const msg = err.message && !err.message.startsWith('API Error:') ? err.message : null;
      if (isSignupMode) {
        errorMsg.textContent = msg || 'Failed to sign up. Please try again or check your inputs.';
      } else {
        errorMsg.textContent = msg || 'Invalid email or password.';
      }
      errorMsg.style.display = 'block';
    } finally {
      submitBtn.textContent = isSignupMode ? 'Sign Up' : 'Login';
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
