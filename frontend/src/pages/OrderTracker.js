import { createCard } from '../components/Card.js';

export function renderOrderTracker(container, orderId) {
  const wrapper = document.createElement('div');
  wrapper.className = 'tracker-wrapper';

  const title = document.createElement('h1');
  title.className = 'section-title justify-center';
  title.textContent = 'Order Status';
  wrapper.appendChild(title);

  const trackerCard = createCard({ className: 'tracker-card' });
  
  const header = document.createElement('div');
  header.className = 'tracker-header';
  header.innerHTML = `
    <span class="text-muted">Order ID: ${orderId}</span>
    <h2 class="tracker-store-name">Delicious Pizza 12</h2>
  `;
  
  // Progress Bar / Steps
  const stepsContainer = document.createElement('div');
  stepsContainer.className = 'tracker-steps';
  
  const steps = [
    { id: 'PENDING', label: 'Order Received', icon: '📝' },
    { id: 'CONFIRMED', label: 'Accepted', icon: '✅' },
    { id: 'COOKING', label: 'Cooking', icon: '👨‍🍳' },
    { id: 'DELIVERING', label: 'On the way', icon: '🛵' },
    { id: 'DELIVERED', label: 'Delivered', icon: '🎉' }
  ];

  // Let's mock the current status as COOKING
  const currentStatusIndex = 2; // COOKING

  const progressLine = document.createElement('div');
  progressLine.className = 'tracker-progress-line';
  // Calculate percentage for progress fill
  const progressPercent = (currentStatusIndex / (steps.length - 1)) * 100;
  progressLine.innerHTML = `<div class="tracker-progress-fill" style="width: ${progressPercent}%"></div>`;
  stepsContainer.appendChild(progressLine);

  const stepsList = document.createElement('ul');
  stepsList.className = 'tracker-steps-list';
  
  steps.forEach((step, index) => {
    const li = document.createElement('li');
    li.className = 'tracker-step-item';
    
    if (index < currentStatusIndex) {
      li.classList.add('completed');
    } else if (index === currentStatusIndex) {
      li.classList.add('active');
    } else {
      li.classList.add('pending');
    }

    li.innerHTML = `
      <div class="tracker-step-icon">${step.icon}</div>
      <span class="tracker-step-label">${step.label}</span>
      ${index === currentStatusIndex ? '<div class="tracker-pulse"></div>' : ''}
    `;
    stepsList.appendChild(li);
  });

  stepsContainer.appendChild(stepsList);

  const etaContainer = document.createElement('div');
  etaContainer.className = 'tracker-eta';
  etaContainer.innerHTML = `
    <p class="text-muted text-sm">Estimated Delivery</p>
    <p class="eta-time">7:45 PM - 8:00 PM</p>
  `;

  trackerCard.appendChild(header);
  trackerCard.appendChild(stepsContainer);
  trackerCard.appendChild(etaContainer);

  const actionHelp = document.createElement('div');
  actionHelp.className = 'tracker-help text-center mt-lg text-muted';
  actionHelp.innerHTML = `<p>Need help? <a href="#" class="text-primary">Contact Support</a></p>`;

  wrapper.appendChild(trackerCard);
  wrapper.appendChild(actionHelp);

  container.appendChild(wrapper);
}
