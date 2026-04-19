import React, { useState, useEffect } from 'react';
import { 
  Shield, 
  Power, 
  Globe, 
  Settings, 
  CreditCard, 
  HelpCircle, 
  ChevronRight,
  ArrowLeft,
  Wifi,
  Battery,
  Signal,
  Lock,
  Search,
  Check
} from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

// Mock Data
const NODES = [
  { id: '1', name: 'USA - New York', cc: 'US', ping: 42, load: 12 },
  { id: '2', name: 'UK - London', cc: 'GB', ping: 88, load: 45 },
  { id: '3', name: 'Germany - Frankfurt', cc: 'DE', ping: 95, load: 68 },
  { id: '4', name: 'Japan - Tokyo', cc: 'JP', ping: 160, load: 30 },
  { id: '5', name: 'Singapore', cc: 'SG', ping: 210, load: 85 },
];

export default function App() {
  const [screen, setScreen] = useState('home');
  const [isConnected, setIsConnected] = useState(false);
  const [isConnecting, setIsConnecting] = useState(false);
  const [selectedNode, setSelectedNode] = useState(NODES[0]);
  const [time, setTime] = useState(new Date());

  useEffect(() => {
    const timer = setInterval(() => setTime(new Date()), 1000);
    return () => clearInterval(timer);
  }, []);

  const handleConnect = () => {
    if (isConnected) {
      setIsConnected(false);
    } else {
      setIsConnecting(true);
      setTimeout(() => {
        setIsConnecting(false);
        setIsConnected(true);
      }, 2000);
    }
  };

  return (
    <div className="min-h-screen bg-[#F0F2F5] flex items-center justify-center p-4">
      {/* Mobile Frame */}
      <div className="w-[375px] h-[812px] bg-white rounded-[60px] shadow-2xl overflow-hidden border-[8px] border-[#1C1C1E] relative">
        {/* Status Bar */}
        <div className="h-11 px-6 pt-3 flex justify-between items-center bg-white z-50">
          <span className="text-[14px] font-bold">
            {time.getHours()}:{time.getMinutes().toString().padStart(2, '0')}
          </span>
          <div className="flex items-center gap-1.5 grayscale">
            <Signal className="w-4 h-4" />
            <Wifi className="w-4 h-4" />
            <Battery className="w-5 h-5" />
          </div>
        </div>

        {/* Dynamic Island Placeholder */}
        <div className="absolute top-2 left-1/2 -translate-x-1/2 w-24 h-6 bg-black rounded-full z-50" />

        {/* App Content */}
        <div className="h-full pt-2 flex flex-col bg-[#F8FAFC] relative">
          <AnimatePresence mode="wait">
            {screen === 'home' && (
              <motion.div
                key="home"
                initial={{ opacity: 0, x: 20 }}
                animate={{ opacity: 1, x: 0 }}
                exit={{ opacity: 0, x: -20 }}
                className="flex-1 overflow-y-auto pb-24"
              >
                <HomeScreen 
                  isConnected={isConnected} 
                  isConnecting={isConnecting} 
                  selectedNode={selectedNode}
                  onConnect={handleConnect}
                  onNavigateServers={() => setScreen('servers')}
                />
              </motion.div>
            )}
            {screen === 'servers' && (
              <motion.div
                key="servers"
                initial={{ opacity: 0, x: 20 }}
                animate={{ opacity: 1, x: 0 }}
                exit={{ opacity: 0, x: -20 }}
                className="absolute inset-0 bg-white z-10 overflow-y-auto"
              >
                <ServersScreen 
                  onBack={() => setScreen('home')}
                  onSelect={(node: any) => {
                    setSelectedNode(node);
                    setScreen('home');
                  }}
                />
              </motion.div>
            )}
            {screen === 'settings' && (
              <motion.div
                key="settings"
                initial={{ opacity: 0, x: 20 }}
                animate={{ opacity: 1, x: 0 }}
                exit={{ opacity: 0, x: -20 }}
                className="flex-1 overflow-y-auto pb-24"
              >
                <SettingsScreen onBack={() => setScreen('home')} />
              </motion.div>
            )}
            {screen === 'subscription' && (
              <motion.div
                key="subscription"
                initial={{ opacity: 0, x: 20 }}
                animate={{ opacity: 1, x: 0 }}
                exit={{ opacity: 0, x: -20 }}
                className="flex-1 overflow-y-auto pb-24"
              >
                <SubscriptionScreen onBack={() => setScreen('home')} />
              </motion.div>
            )}
          </AnimatePresence>

          {/* Bottom Bar Interaction Simulation */}
          {screen !== 'servers' && (
            <div className="absolute bottom-0 w-full bg-white/80 backdrop-blur-xl border-t border-slate-200 px-6 py-4 flex justify-between items-center rounded-t-3xl shadow-[0_-1px_10px_rgba(0,0,0,0.05)]">
               <NavItem active={screen === 'home'} icon={Shield} onClick={() => setScreen('home')} />
               <NavItem active={screen === 'servers'} icon={Globe} onClick={() => setScreen('servers')} />
               <NavItem active={screen === 'subscription'} icon={CreditCard} onClick={() => setScreen('subscription')} />
               <NavItem active={screen === 'settings'} icon={Settings} onClick={() => setScreen('settings')} />
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

function NavItem({ active, icon: Icon, onClick }: { active: boolean, icon: any, onClick: () => void }) {
  return (
    <button onClick={onClick} className={`p-2 rounded-xl transition-all ${active ? 'bg-blue-50 text-blue-600 scale-110' : 'text-slate-400'}`}>
      <Icon className="w-6 h-6" />
    </button>
  );
}

function HomeScreen({ isConnected, isConnecting, selectedNode, onConnect, onNavigateServers }: any) {
  return (
    <div className="px-6 py-4">
      <div className="flex justify-between items-center mb-10">
        <div>
          <h2 className="text-2xl font-black tracking-tighter text-slate-800">SWIMTUNEL<span className="text-blue-600">VPN</span></h2>
          <p className="text-[10px] font-bold uppercase tracking-widest text-slate-400">Secure Protocol v2.4</p>
        </div>
        <div className="bg-white p-2 rounded-full shadow-sm border border-slate-100">
          <HelpCircle className="w-5 h-5 text-slate-400" />
        </div>
      </div>

      <div className="flex flex-col items-center justify-center py-10">
        <div className="relative mb-12">
          <AnimatePresence>
            {(isConnected || isConnecting) && (
              <motion.div
                initial={{ scale: 0.8, opacity: 0 }}
                animate={{ scale: 1.5, opacity: [0, 0.3, 0] }}
                transition={{ repeat: Infinity, duration: 2 }}
                className={`absolute inset-0 rounded-full ${isConnecting ? 'bg-amber-400' : 'bg-blue-400'}`}
              />
            )}
          </AnimatePresence>
          
          <button 
            onClick={onConnect}
            disabled={isConnecting}
            className={`relative w-48 h-48 rounded-full flex flex-col items-center justify-center shadow-2xl transition-all active:scale-95 border-[6px] 
              ${isConnecting ? 'bg-amber-50 border-amber-200' : isConnected ? 'bg-blue-50 border-blue-200' : 'bg-white border-slate-100'}`}
          >
            <Power className={`w-16 h-16 mb-2 ${isConnecting ? 'text-amber-500 animate-pulse' : isConnected ? 'text-blue-600' : 'text-slate-300'}`} />
            <span className={`text-[10px] font-black uppercase tracking-[0.2em] ${isConnecting ? 'text-amber-600' : isConnected ? 'text-blue-600' : 'text-slate-400'}`}>
              {isConnecting ? 'Securing...' : isConnected ? 'Connected' : 'Protect Me'}
            </span>
          </button>
        </div>

        <div className="text-center mb-12">
          <div className="inline-flex items-center gap-2 px-4 py-1.5 rounded-full bg-slate-100 mb-2 border border-slate-200">
             <div className={`w-2 h-2 rounded-full animate-pulse ${isConnected ? 'bg-green-500' : 'bg-red-500'}`} />
             <span className="text-[10px] font-bold uppercase tracking-widest text-slate-600">
               {isConnected ? 'Traffic Encrypted' : 'Connection Unsecured'}
             </span>
          </div>
          <p className="text-sm font-medium text-slate-400 text-center">
            {isConnected ? 'Your IP is hidden from trackers' : 'Change your location below'}
          </p>
        </div>
      </div>

      <button 
        onClick={onNavigateServers}
        className="w-full bg-white border border-slate-200 p-5 rounded-3xl flex items-center justify-between shadow-sm hover:border-blue-200 group transition-all"
      >
        <div className="flex items-center gap-4">
          <div className="w-12 h-12 bg-blue-50 rounded-2xl flex items-center justify-center group-hover:scale-110 transition-transform">
            <Globe className="w-6 h-6 text-blue-600" />
          </div>
          <div className="text-left">
            <p className="text-[10px] font-bold uppercase tracking-wider text-slate-400">Selected Server</p>
            <p className="font-bold text-slate-800">{selectedNode.name}</p>
          </div>
        </div>
        <ChevronRight className="w-5 h-5 text-slate-300 group-hover:translate-x-1 transition-transform" />
      </button>
    </div>
  );
}

function ServersScreen({ onBack, onSelect }: any) {
  const [search, setSearch] = useState('');
  const filtered = NODES.filter(n => n.name.toLowerCase().includes(search.toLowerCase()));

  return (
    <div className="px-6 py-4 flex flex-col h-full bg-white">
      <div className="flex items-center gap-4 mb-6">
        <button onClick={onBack} className="p-2 -ml-2 text-slate-400 hover:text-slate-800 transition-colors">
          <ArrowLeft className="w-6 h-6" />
        </button>
        <h2 className="text-xl font-black text-slate-800">Locations</h2>
      </div>

      <div className="relative mb-6">
        <Search className="absolute left-4 top-1/2 -translate-y-1/2 w-4 h-4 text-slate-400" />
        <input 
          type="text" 
          placeholder="Search..."
          className="w-full h-12 bg-slate-100 rounded-2xl pl-12 pr-4 text-sm font-medium border-none focus:ring-2 focus:ring-blue-100 transition-all outline-none"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      <div className="space-y-4">
        {filtered.map(node => (
          <button 
            key={node.id} 
            onClick={() => onSelect(node)}
            className="w-full flex items-center justify-between p-4 rounded-3xl border border-slate-100 hover:border-blue-200 bg-white transition-all group"
          >
            <div className="flex items-center gap-4">
              <div className="w-10 h-10 bg-slate-50 rounded-xl flex items-center justify-center font-bold text-slate-400 text-xs text-center">
                {node.cc}
              </div>
              <div className="text-left">
                <p className="font-bold text-slate-800 group-hover:text-blue-600 transition-colors uppercase">{node.name}</p>
                <div className="flex items-center gap-2">
                  <span className="text-[10px] font-bold text-green-500 uppercase">{node.ping}ms</span>
                  <span className="text-[10px] font-bold text-slate-300 uppercase">|</span>
                  <span className="text-[10px] font-bold text-slate-400 uppercase">{node.load}% Load</span>
                </div>
              </div>
            </div>
            <ChevronRight className="w-4 h-4 text-slate-200 group-hover:translate-x-1 transition-transform" />
          </button>
        ))}
      </div>
    </div>
  );
}

function SettingsScreen({ onBack }: any) {
  return (
    <div className="px-6 py-4">
      <div className="flex items-center gap-4 mb-10">
        <button onClick={onBack} className="p-2 -ml-2 text-slate-400">
          <ArrowLeft className="w-6 h-6" />
        </button>
        <h2 className="text-xl font-black text-slate-800">Preferences</h2>
      </div>

      <div className="space-y-6">
        <SettingsGroup title="Connection">
          <ToggleItem label="Auto-Connect" description="Start VPN on system boot" defaultChecked />
          <ToggleItem label="Kill Switch" description="Block data if VPN drops" />
        </SettingsGroup>

        <SettingsGroup title="Protocol">
          <SelectItem label="Security Protocol" value="VLESS" />
          <SelectItem label="Port Selection" value="Auto (443)" />
        </SettingsGroup>
        
        <div className="pt-4 text-center">
          <p className="text-[10px] font-bold text-slate-300 uppercase tracking-widest text-center">Version 1.0.0-mvp</p>
        </div>
      </div>
    </div>
  );
}

function SubscriptionScreen({ onBack }: any) {
  return (
    <div className="px-6 py-4">
       <div className="flex items-center gap-4 mb-10">
        <button onClick={onBack} className="p-2 -ml-2 text-slate-400">
          <ArrowLeft className="w-6 h-6" />
        </button>
        <h2 className="text-xl font-black text-slate-800">Your Plan</h2>
      </div>

      <div className="bg-gradient-to-br from-blue-600 to-blue-800 p-6 rounded-[32px] text-white shadow-xl shadow-blue-100 mb-8 overflow-hidden relative">
        <div className="absolute top-0 right-0 p-4 opacity-10">
           <Shield className="w-32 h-32" />
        </div>
        <div className="relative z-10">
          <p className="text-[10px] font-black uppercase tracking-widest opacity-60 mb-2">Current Active Status</p>
          <h3 className="text-2xl font-black mb-6">PREMIUM PLAN</h3>
          
          <div className="space-y-4 mb-8">
            <PlanFeat label="Unlimited Bandwidth" />
            <PlanFeat label="High Speed (10G Nodes)" />
            <PlanFeat label="Up to 5 Devices" />
          </div>

          <div className="flex justify-between items-end">
            <div>
              <p className="text-[10px] font-bold opacity-60">Renews on</p>
              <p className="font-bold">May 19, 2026</p>
            </div>
            <Lock className="w-6 h-6 opacity-30" />
          </div>
        </div>
      </div>

      <button className="w-full bg-slate-800 text-white p-5 rounded-3xl font-bold shadow-lg hover:bg-slate-900 transition-colors">
        Manage Subscription
      </button>
    </div>
  );
}

function PlanFeat({ label }: { label: string }) {
  return (
    <div className="flex items-center gap-2">
      <Check className="w-4 h-4 text-blue-200" />
      <span className="text-sm font-semibold text-blue-50">{label}</span>
    </div>
  );
}

function ToggleItem({ label, description, defaultChecked = false }: { label: string, description: string, defaultChecked?: boolean }) {
  const [checked, setChecked] = useState(defaultChecked);
  return (
    <div className="flex items-center justify-between py-2">
      <div>
        <p className="font-bold text-slate-800">{label}</p>
        <p className="text-[10px] font-medium text-slate-400 uppercase tracking-tighter">{description}</p>
      </div>
      <button 
        onClick={() => setChecked(!checked)}
        className={`w-12 h-6 rounded-full p-1 transition-colors ${checked ? 'bg-blue-600' : 'bg-slate-200'}`}
      >
        <div className={`w-4 h-4 bg-white rounded-full transition-transform ${checked ? 'translate-x-6' : 'translate-x-0'}`} />
      </button>
    </div>
  );
}

function SelectItem({ label, value }: { label: string, value: string }) {
  return (
    <div className="flex items-center justify-between py-2 cursor-pointer group">
      <p className="font-bold text-slate-800">{label}</p>
      <div className="flex items-center gap-2">
        <span className="text-sm font-bold text-blue-600">{value}</span>
        <ChevronRight className="w-4 h-4 text-slate-300 group-hover:translate-x-1 transition-all" />
      </div>
    </div>
  );
}

function SettingsGroup({ title, children }: { title: string, children: React.ReactNode }) {
  return (
    <div className="space-y-2">
      <h4 className="text-[10px] font-black uppercase tracking-[0.2em] text-slate-300">{title}</h4>
      <div className="bg-white p-6 rounded-3xl border border-slate-100 shadow-sm space-y-4">
        {children}
      </div>
    </div>
  );
}
