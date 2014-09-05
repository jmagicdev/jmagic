package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Chained to the Rocks")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Theros.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class ChainedtotheRocks extends Card
{
	public static final class ChainedtotheRocksAbility1 extends EventTriggeredAbility
	{
		public ChainedtotheRocksAbility1(GameState state)
		{
			super(state, "When Chained to the Rocks enters the battlefield, exile target creature an opponent controls until Chained to the Rocks leaves the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator opponentsCreatures = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
			SetGenerator target = targetedBy(this.addTarget(opponentsCreatures, "target creature an opponent controls"));

			state.ensureTracker(new LeftTheBattlefield.LeavesTheBattlefieldTracker());
			SetGenerator thisIsGone = Intersect.instance(ABILITY_SOURCE_OF_THIS, LeftTheBattlefield.instance());

			EventFactory exileUntil = new EventFactory(EventType.EXILE_UNTIL, "Exile target creature an opponent controls until Chained to the Rocks leaves the battlefield.");
			exileUntil.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exileUntil.parameters.put(EventType.Parameter.OBJECT, target);
			exileUntil.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(thisIsGone));
			this.addEffect(exileUntil);
		}
	}

	public ChainedtotheRocks(GameState state)
	{
		super(state);

		// Enchant Mountain you control
		SetGenerator mountainsYouControl = Intersect.instance(HasSubType.instance(SubType.MOUNTAIN), ControlledBy.instance(You.instance()));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Final(state, "Mountain you control", mountainsYouControl));

		// When Chained to the Rocks enters the battlefield, exile target
		// creature an opponent controls until Chained to the Rocks leaves the
		// battlefield. (That creature returns under its owner's control.)
		this.addAbility(new ChainedtotheRocksAbility1(state));
	}
}
