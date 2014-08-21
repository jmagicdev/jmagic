package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class DealDamageBatches extends EventType
{
	public static final EventType INSTANCE = new DealDamageBatches();

	private DealDamageBatches()
	{
		super("DEAL_DAMAGE_BATCHES");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.TARGET;
	}

	private boolean fakeAbilityForDamage(DamageAssignment.Batch damage, Class<? extends Keyword> ability, GameState state)
	{
		for(java.util.Map.Entry<Integer, ContinuousEffectType.DamageAbility> entry: state.dealDamageAsThoughHasAbility.entrySet())
			if(!entry.getValue().dp.match(damage, state.get(entry.getKey()), state).isEmpty())
				if(entry.getValue().k.isAssignableFrom(ability))
					return true;
		return false;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		// the structure of these maps is:
		// map<player, map<source, amount>>
		java.util.Map<Player, java.util.Map<GameObject, Integer>> lifeLosses = new java.util.HashMap<Player, java.util.Map<GameObject, Integer>>();
		java.util.Map<Player, java.util.Map<GameObject, Integer>> lifeGains = new java.util.HashMap<Player, java.util.Map<GameObject, Integer>>();
		// the maps are split up in this manner because of this rule:
		// 118.9. Some triggered abilities are written, "Whenever [a player]
		// gains life, . . . ." Such abilities are treated as though they
		// are written, "Whenever a source causes [a player] to gain life, .
		// . . ."

		java.util.Map<GameObject, Integer> creaturesGettingCounters = new java.util.HashMap<GameObject, Integer>();
		java.util.Map<Player, Integer> playersGettingPoisonCounters = new java.util.HashMap<Player, Integer>();
		java.util.Map<GameObject, Integer> planeswalkersLosingCounters = new java.util.HashMap<GameObject, Integer>();

		java.util.Set<DamageAssignment> assignments = parameters.get(Parameter.TARGET).getAll(DamageAssignment.class);
		for(DamageAssignment assignment: assignments)
		{
			// for checking as-though effects:
			DamageAssignment.Batch batch = new DamageAssignment.Batch();
			batch.add(assignment);

			GameObject source = game.actualState.get(assignment.sourceID);
			Identified taker = game.actualState.get(assignment.takerID);

			boolean lifelink = source.hasAbility(org.rnd.jmagic.abilities.keywords.Lifelink.class);
			if(!lifelink)
				lifelink = fakeAbilityForDamage(batch, org.rnd.jmagic.abilities.keywords.Lifelink.class, game.actualState);
			if(lifelink)
			{
				Player controller = source.getController(source.state);
				if(lifeGains.containsKey(controller))
				{
					java.util.Map<GameObject, Integer> lifeGain = lifeGains.get(controller);
					if(lifeGain.containsKey(source))
						lifeGain.put(source, lifeGain.get(source) + 1);
					else
						lifeGain.put(source, 1);
				}
				else
				{
					java.util.Map<GameObject, Integer> lifeGain = new java.util.HashMap<GameObject, Integer>();
					lifeGain.put(source, 1);
					lifeGains.put(controller, lifeGain);
				}
			}

			boolean infect = source.hasAbility(org.rnd.jmagic.abilities.keywords.Infect.class);
			if(!infect)
				infect = fakeAbilityForDamage(batch, org.rnd.jmagic.abilities.keywords.Infect.class, game.actualState);
			if(taker.isPlayer())
			{
				Player losingLife = (Player)taker;
				if(infect)
				{
					if(!playersGettingPoisonCounters.containsKey(losingLife))
						playersGettingPoisonCounters.put(losingLife, 1);
					else
						// can't ++ an Integer
						playersGettingPoisonCounters.put(losingLife, playersGettingPoisonCounters.get(losingLife) + 1);
				}
				else
				{
					if(lifeLosses.containsKey(losingLife))
					{
						java.util.Map<GameObject, Integer> lifeLoss = lifeLosses.get(losingLife);
						if(lifeLoss.containsKey(source))
							lifeLoss.put(source, lifeLoss.get(source) + 1);
						else
							lifeLoss.put(source, 1);
					}
					else
					{
						java.util.Map<GameObject, Integer> lifeLoss = new java.util.HashMap<GameObject, Integer>();
						lifeLoss.put(source, 1);
						lifeLosses.put(losingLife, lifeLoss);
					}
				}

				continue;
			}

			// if it's not an object, we'll get a class-cast exception here
			GameObject takerObject = (GameObject)taker;
			if(takerObject.getTypes().contains(Type.CREATURE))
			{
				// If the source has wither/infect add -1/-1 counters,
				// otherwise, increment damage
				boolean wither = source.hasAbility(org.rnd.jmagic.abilities.keywords.Wither.class);
				if(!wither)
					wither = fakeAbilityForDamage(batch, org.rnd.jmagic.abilities.keywords.Wither.class, game.actualState);
				if(wither || infect)
				{
					if(!creaturesGettingCounters.containsKey(takerObject))
						creaturesGettingCounters.put(takerObject, 1);
					else
						// can't ++ an Integer
						creaturesGettingCounters.put(takerObject, creaturesGettingCounters.get(takerObject) + 1);
				}
				else
				{
					// Mark any creature damaged by deathtouch so SBAs can
					// destroy them
					GameObject physical = takerObject.getPhysical();
					if(source.hasAbility(org.rnd.jmagic.abilities.keywords.Deathtouch.class))
						physical.setDamagedByDeathtouchSinceLastSBA(true);
					physical.setDamage(physical.getDamage() + 1);
				}
			}
			if(takerObject.getTypes().contains(Type.PLANESWALKER))
			{
				if(!planeswalkersLosingCounters.containsKey(takerObject))
					planeswalkersLosingCounters.put(takerObject, 1);
				else
					// can't ++ an Integer
					planeswalkersLosingCounters.put(takerObject, planeswalkersLosingCounters.get(takerObject) + 1);
			}
		}

		for(java.util.Map.Entry<Player, Integer> playerPoisonCounter: playersGettingPoisonCounters.entrySet())
		{
			Player player = playerPoisonCounter.getKey();
			int number = playerPoisonCounter.getValue();

			java.util.Map<Parameter, Set> witherParameters = new java.util.HashMap<Parameter, Set>();
			witherParameters.put(Parameter.PLAYER, new Set(player));
			witherParameters.put(Parameter.NUMBER, new Set(number));
			createEvent(game, "Put " + number + " poison counter" + (number == 1 ? "" : "s") + " on " + player + ".", ADD_POISON_COUNTERS, witherParameters).perform(event, false);
		}

		for(java.util.Map.Entry<Player, java.util.Map<GameObject, Integer>> playerLifeGain: lifeGains.entrySet())
		{
			Player player = playerLifeGain.getKey();
			for(java.util.Map.Entry<GameObject, Integer> lifeGain: playerLifeGain.getValue().entrySet())
			{
				java.util.Map<Parameter, Set> gainLifeParameters = new java.util.HashMap<Parameter, Set>();
				gainLifeParameters.put(Parameter.CAUSE, new Set(lifeGain.getKey()));
				gainLifeParameters.put(Parameter.PLAYER, new Set(player));
				gainLifeParameters.put(Parameter.NUMBER, new Set(lifeGain.getValue()));
				createEvent(game, player + " gains " + lifeGain.getValue() + " life.", GAIN_LIFE, gainLifeParameters).perform(event, false);
			}
		}

		for(java.util.Map.Entry<Player, java.util.Map<GameObject, Integer>> playerLifeLoss: lifeLosses.entrySet())
		{
			Player player = playerLifeLoss.getKey();
			for(java.util.Map.Entry<GameObject, Integer> lifeLoss: playerLifeLoss.getValue().entrySet())
			{
				java.util.Map<Parameter, Set> loseLifeParameters = new java.util.HashMap<Parameter, Set>();
				loseLifeParameters.put(Parameter.CAUSE, new Set(lifeLoss.getKey()));
				loseLifeParameters.put(Parameter.PLAYER, new Set(player));
				loseLifeParameters.put(Parameter.NUMBER, new Set(lifeLoss.getValue()));
				loseLifeParameters.put(Parameter.DAMAGE, Empty.set);
				createEvent(game, player + " loses " + lifeLoss.getValue() + " life.", LOSE_LIFE, loseLifeParameters).perform(event, false);
			}
		}

		// same for creatures and -1/-1 counters
		for(java.util.Map.Entry<GameObject, Integer> witherCounter: creaturesGettingCounters.entrySet())
		{
			GameObject taker = witherCounter.getKey();
			int number = witherCounter.getValue();

			java.util.Map<Parameter, Set> witherParameters = new java.util.HashMap<Parameter, Set>();
			witherParameters.put(Parameter.CAUSE, new Set(game));
			witherParameters.put(Parameter.COUNTER, new Set(Counter.CounterType.MINUS_ONE_MINUS_ONE));
			witherParameters.put(Parameter.NUMBER, new Set(number));
			witherParameters.put(Parameter.OBJECT, new Set(taker));
			createEvent(game, "Put " + number + " -1/-1 counter" + (number == 1 ? "" : "s") + " on " + taker + ".", PUT_COUNTERS, witherParameters).perform(event, false);
		}

		for(java.util.Map.Entry<GameObject, Integer> loyaltyCounter: planeswalkersLosingCounters.entrySet())
		{
			GameObject taker = loyaltyCounter.getKey();
			int number = planeswalkersLosingCounters.get(taker);

			java.util.Map<Parameter, Set> removeCounterParameters = new java.util.HashMap<Parameter, Set>();
			removeCounterParameters.put(Parameter.CAUSE, new Set(game));
			removeCounterParameters.put(Parameter.COUNTER, new Set(Counter.CounterType.LOYALTY));
			removeCounterParameters.put(Parameter.NUMBER, new Set(number));
			removeCounterParameters.put(Parameter.OBJECT, new Set(taker));

			createEvent(game, "Remove " + number + " loyalty counter" + (number == 1 ? "" : "s") + " from " + taker + ".", REMOVE_COUNTERS, removeCounterParameters).perform(event, false);
		}

		// If we get as far as this event type, all the damage here will be
		// dealt. No need to check for damage not being dealt, so we just
		// add the assignments directly to the result. -RulesGuru
		event.setResult(Identity.fromCollection(assignments));

		return true;
	}
}