export function createCard({ children = [], hoverable = false, className = '' }) {
  const card = document.createElement('div');
  card.className = `card ${hoverable ? 'card-hoverable' : ''} ${className}`;
  
  children.forEach(child => {
    if (typeof child === 'string') {
      card.innerHTML += child;
    } else if (child instanceof Node) {
      card.appendChild(child);
    }
  });
  
  return card;
}
