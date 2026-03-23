export function createInput({ type = 'text', placeholder, value = '', id = '', label = '', required = false, onChange }) {
  const container = document.createElement('div');
  container.className = 'input-group';
  
  if (label) {
    const lbl = document.createElement('label');
    lbl.className = 'input-label';
    lbl.htmlFor = id;
    lbl.textContent = label;
    if (required) {
      const req = document.createElement('span');
      req.className = 'input-required';
      req.textContent = ' *';
      lbl.appendChild(req);
    }
    container.appendChild(lbl);
  }
  
  const input = document.createElement('input');
  input.type = type;
  input.className = 'input-control';
  if (id) input.id = id;
  if (placeholder) input.placeholder = placeholder;
  input.value = value;
  if (required) input.required = true;
  
  if (onChange) {
    input.addEventListener('input', onChange);
  }
  
  container.appendChild(input);
  return container;
}
