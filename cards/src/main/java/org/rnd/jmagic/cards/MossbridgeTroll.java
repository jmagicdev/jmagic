package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mossbridge Troll")
@Types({Type.CREATURE})
@SubTypes({SubType.TROLL})
@ManaCost("5GG")
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class MossbridgeTroll extends Card
{
	public static final class PermaRegen extends StaticAbility
	{
		public PermaRegen(GameState state)
		{
			super(state, "If Mossbridge Troll would be destroyed, regenerate it.");

			SimpleEventPattern thisIsDestroyed = new SimpleEventPattern(EventType.DESTROY_ONE_PERMANENT);
			thisIsDestroyed.put(EventType.Parameter.PERMANENT, This.instance());

			// I might not be willing to put the wording I want into the ability
			// itself, but god dammit I will embed my opinions on regeneration
			// anywhere I can get it.
			EventFactory regenerateThis = new EventFactory(EventType.REGENERATE, "Mossbridge Troll regenerates");
			regenerateThis.parameters.put(EventType.Parameter.CAUSE, This.instance());
			regenerateThis.parameters.put(EventType.Parameter.OBJECT, This.instance());

			// And again. Fuck ambiguity when it's easy to remove.
			EventReplacementEffect shield = new EventReplacementEffect(state.game, "If Mossbridge Troll would be destroyed, instead it regenerates", thisIsDestroyed);
			shield.addEffect(regenerateThis);
			this.addEffectPart(replacementEffectPart(shield));
		}
	}

	/**
	 * @eparam CAUSE: Mossbridge Troll's activated ability
	 * @eparam PLAYER: controller of CAUSE
	 * @eparam OBJECT: creatures PLAYER controls other than Mossbridge Troll
	 * @eparam RESULT: empty
	 */
	public static final EventType MOSSBRIDGE_TROLL_COST = new EventType("MOSSBRIDGE_TROLL_COST")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set creatures = parameters.get(Parameter.OBJECT);
			Set cantBeTapped = new Set();

			int totalPowerAvailable = 0;
			for(GameObject creature: creatures.getAll(GameObject.class))
			{
				java.util.Map<Parameter, Set> tapParameters = new java.util.HashMap<Parameter, Set>();
				tapParameters.put(Parameter.CAUSE, cause);
				tapParameters.put(Parameter.OBJECT, new Set(creature));
				Event tap = createEvent(game, "Tap " + creature, TAP_PERMANENTS, tapParameters);
				if(!tap.attempt(event))
				{
					cantBeTapped.add(creature);
					continue;
				}

				if(creature.getPower() <= 0)
					continue;

				totalPowerAvailable += creature.getPower();
			}
			if(totalPowerAvailable < 10)
			{
				event.allChoicesMade = false;
				return;
			}

			Player you = parameters.get(Parameter.PLAYER).getOne(Player.class);
			creatures.removeAll(cantBeTapped);
			while(true)
			{
				java.util.List<GameObject> choices = you.sanitizeAndChoose(game.actualState, 0, null, creatures.getAll(GameObject.class), PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.TAP);
				int totalPower = 0;
				for(GameObject chosen: choices)
				{
					totalPower += chosen.getPower();
					if(totalPower >= 10)
					{
						event.allChoicesMade = true;
						event.putChoices(you, choices);
						return;
					}
				}
			}
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);
			if(!event.allChoicesMade)
				return false;

			java.util.Map<Parameter, Set> tapParameters = new java.util.HashMap<Parameter, Set>();
			tapParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			tapParameters.put(Parameter.OBJECT, event.getChoices(parameters.get(Parameter.PLAYER).getOne(Player.class)));
			Event tap = createEvent(game, "Tap stuff", TAP_PERMANENTS, tapParameters);
			tap.perform(event, true);
			return true;
		}
	};

	public static final class BigPump extends ActivatedAbility
	{
		public BigPump(GameState state)
		{
			super(state, "Tap any number of untapped creatures you control other than Mossbridge Troll with total power 10 or greater: Mossbridge Troll gets +20/+20 until end of turn.");

			// Tap any number of untapped creatures you control other than
			// Mossbridge Troll with total power 10 or greater
			EventFactory cost = new EventFactory(MOSSBRIDGE_TROLL_COST, "Tap any number of untapped creatures you control other than Mossbridge Troll with total power 10 or greater");
			cost.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cost.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cost.parameters.put(EventType.Parameter.OBJECT, RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS));
			this.addCost(cost);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +20, +20, "Mossbridge Troll gets +20/+20 until end of turn."));
		}
	}

	public MossbridgeTroll(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// If Mossbridge Troll would be destroyed, regenerate it.
		this.addAbility(new PermaRegen(state));

		// Tap any number of untapped creatures you control other than
		// Mossbridge Troll with total power 10 or greater: Mossbridge Troll
		// gets +20/+20 until end of turn.
		this.addAbility(new BigPump(state));
	}
}
