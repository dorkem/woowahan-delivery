export function createChip({ text, active = false, onClick }) {
  const chip = document.createElement('button');
  chip.type = 'button';
  chip.className = `chip ${active ? 'chip-active' : ''}`;
  chip.textContent = text;
  
  if (onClick) {
    chip.addEventListener('click', (e) => {
      // Toggle visual state
      const isCurrentlyActive = chip.classList.contains('chip-active');
      if (isCurrentlyActive) {
        chip.classList.remove('chip-active');
      } else {
        // Optional: logic to enforce single active chip could go here, 
        // but typically handled by parent component managing state.
        chip.classList.add('chip-active');
      }
      onClick(e, !isCurrentlyActive);
    });
  }
  
  return chip;
}
