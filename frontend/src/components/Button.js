/* Component: Button */
export function createButton({ text, variant = 'primary', size = 'md', onClick, type = 'button', icon = null }) {
  const btn = document.createElement('button');
  btn.type = type;
  btn.className = `btn btn-${variant} btn-${size}`;
  
  if (icon) {
    const iconSpan = document.createElement('span');
    iconSpan.className = 'btn-icon';
    iconSpan.innerHTML = icon;
    btn.appendChild(iconSpan);
  }

  const textSpan = document.createElement('span');
  textSpan.textContent = text;
  btn.appendChild(textSpan);

  if (onClick) {
    btn.addEventListener('click', onClick);
  }

  return btn;
}

/* Base Styles (Insert into main CSS dynamically or define in separate CSS file - we will define in components.css later) */
