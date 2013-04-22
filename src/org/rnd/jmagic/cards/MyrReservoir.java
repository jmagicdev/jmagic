package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Myr Reservoir")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class MyrReservoir extends Card
{
	public static final class MyrReservoirAbility0 extends ActivatedAbility
	{
		public MyrReservoirAbility0(GameState state)
		{
			super(state, "(T): Add (2) to your mana pool. Spend this mana only to cast Myr spells or activate abilities of Myr.");
			this.costsTap = true;

			EventFactory factory = new EventFactory(ADD_RESTRICTED_MANA, "Add (2) to your mana pool. Spend this mana only to cast Myr spells or activate abilities of Myr.");
			factory.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.TYPE, HasSubType.instance(SubType.MYR));
			factory.parameters.put(EventType.Parameter.MANA, Identity.instance(ManaSymbol.ManaType.COLORLESS));
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			this.addEffect(factory);
		}
	}

	public static final class MyrReservoirAbility1 extends ActivatedAbility
	{
		public MyrReservoirAbility1(GameState state)
		{
			super(state, "(3), (T): Return target Myr card from your graveyard to your hand.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;

			SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasSubType.instance(SubType.MYR), InZone.instance(yourGraveyard)), "target Myr card"));

			EventFactory factory = new EventFactory(EventType.MOVE_OBJECTS, "Return target Myr card from your graveyard to your hand.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			factory.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(factory);
		}
	}

	public MyrReservoir(GameState state)
	{
		super(state);

		// (T): Add (2) to your mana pool. Spend this mana only to cast Myr
		// spells or activate abilities of Myr.
		this.addAbility(new MyrReservoirAbility0(state));

		// (3), (T): Return target Myr card from your graveyard to your hand.
		this.addAbility(new MyrReservoirAbility1(state));
	}
}
