package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Boseiju, Who Shelters All")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.LAND})
@ColorIdentity({})
public final class BoseijuWhoSheltersAll extends Card
{
	public static final class BoseijuMana extends ManaSymbol
	{
		private static final long serialVersionUID = 1L;

		public BoseijuMana()
		{
			super(ManaType.COLORLESS);
			this.name = "If this mana is spent on an instant or sorcery spell, that spell can't be countered by spells or abilities.";
		}

		public EventFactory getEffectForSpendingOn(GameObject o)
		{
			java.util.Set<Type> types = o.getTypes();
			if(!types.contains(Type.INSTANT) && !types.contains(Type.SORCERY))
				return null;

			SetGenerator thatSpell = Identity.instance(o);

			SimpleEventPattern counterThatSpell = new SimpleEventPattern(EventType.COUNTER);
			counterThatSpell.put(EventType.Parameter.OBJECT, thatSpell);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(counterThatSpell));

			EventFactory shelter = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "That spell can't be countered.");
			shelter.parameters.put(EventType.Parameter.CAUSE, This.instance());
			shelter.parameters.put(EventType.Parameter.EFFECT, Identity.instance(part));
			return shelter;
		}

		@Override
		public ManaSymbol create()
		{
			return new BoseijuMana();
		}
	}

	public static final class BoseijuWhoSheltersAllAbility1 extends ActivatedAbility
	{
		public BoseijuWhoSheltersAllAbility1(GameState state)
		{
			super(state, "(T), Pay 2 life: Add (1) to your mana pool. If that mana is spent on an instant or sorcery spell, that spell can't be countered by spells or abilities.");
			this.costsTap = true;
			this.addCost(payLife(You.instance(), 2, "Pay 2 life"));

			EventFactory mana = new EventFactory(EventType.ADD_MANA, "Add (1) to your mana pool. If that mana is spent on an instant or sorcery spell, that spell can't be countered by spells or abilities.");
			mana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			mana.parameters.put(EventType.Parameter.MANA, Identity.instance(new BoseijuMana()));
			mana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(mana);
		}
	}

	public BoseijuWhoSheltersAll(GameState state)
	{
		super(state);

		// Boseiju, Who Shelters All enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T), Pay 2 life: Add (1) to your mana pool. If that mana is spent on
		// an instant or sorcery spell, that spell can't be countered by spells
		// or abilities.
		this.addAbility(new BoseijuWhoSheltersAllAbility1(state));
	}
}
