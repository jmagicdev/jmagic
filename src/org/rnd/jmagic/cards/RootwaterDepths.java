package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Rootwater Depths")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class RootwaterDepths extends Card
{
	public static final class UntapDuringSpecificPlayersUntapStep extends SimpleEventPattern
	{
		private int playerID;

		public UntapDuringSpecificPlayersUntapStep(SetGenerator permanents, Player who)
		{
			super(EventType.UNTAP_ONE_PERMANENT);
			this.put(EventType.Parameter.OBJECT, permanents);
			this.playerID = who.ID;
		}

		@Override
		public boolean match(Event event, Identified object, GameState state)
		{
			return (super.match(event, object, state) && (state.currentStep().type == Step.StepType.UNTAP) && (state.currentStep().ownerID == this.playerID));
		}
	}

	/**
	 * This is especially retarded. The card says "during your untap step", not
	 * "during its controllers untap step", which means that the land doesn't
	 * untap during the next untap step of the player under whose control the
	 * ability resolved. This means, for example, that if I have a Seedborn
	 * Muse, you activate this land on my turn, and then I gain control of your
	 * land, it won't untap (under my control) during your untap step.
	 * 
	 * @eparam CAUSE: the mana ability
	 * @eparam SOURCE: the land
	 * @eparam PLAYER: the player that tapped the land
	 * @eparam MANA: the mana to add
	 * @eparam RESULT: the result of ADD_MANA
	 */
	public static final EventType TAP_LAND_EVENT = new EventType("TAP_LAND_EVENT")
	{
		@Override
		public boolean addsMana()
		{
			return true;
		}

		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set player = parameters.get(Parameter.PLAYER);
			Set land = parameters.get(Parameter.SOURCE);
			Set mana = parameters.get(Parameter.MANA);
			java.util.Map<Parameter, Set> manaParameters = new java.util.HashMap<Parameter, Set>();
			manaParameters.put(Parameter.SOURCE, land);
			manaParameters.put(Parameter.PLAYER, player);
			manaParameters.put(Parameter.MANA, mana);
			Event addMana = createEvent(game, "Add mana to your mana pool", ADD_MANA, manaParameters);
			addMana.perform(event, true);

			EventPattern untapping = new UntapDuringSpecificPlayersUntapStep(Identity.instance(land), player.getOne(Player.class));
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(untapping));

			SetGenerator expires = Intersect.instance(PreviousStep.instance(), UntapStepOf.instance(Identity.instance(player)));

			Set ability = parameters.get(Parameter.CAUSE);

			java.util.Map<Parameter, Set> fceParameters = new java.util.HashMap<Parameter, Set>();
			fceParameters.put(Parameter.CAUSE, ability);
			fceParameters.put(Parameter.EFFECT, new Set(part));
			fceParameters.put(Parameter.EXPIRES, new Set(expires));
			Event fce = createEvent(game, land + " doesn't untap during your next untap step.", CREATE_FLOATING_CONTINUOUS_EFFECT, fceParameters);
			fce.perform(event, true);

			event.setResult(addMana.getResult());
			return true;
		}

	};

	public static final class TapForColored extends ActivatedAbility
	{
		public TapForColored(GameState state)
		{
			super(state, "(T): Add (U) or (B) to your mana pool. Rootwater Depths doesn't untap during your next untap step.");
			this.costsTap = true;

			EventFactory effect = new EventFactory(TAP_LAND_EVENT, "Add (U) or (B) to your mana pool. Rootwater Depths doesn't untap during your next untap step.");
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			effect.parameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool("(UB)")));
			this.addEffect(effect);
		}
	}

	public RootwaterDepths(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (T): Add (U) or (B) to your mana pool. Rootwater Depths doesn't untap
		// during your next untap step.
		this.addAbility(new TapForColored(state));
	}
}
