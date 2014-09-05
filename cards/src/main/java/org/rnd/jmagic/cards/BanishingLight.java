package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Banishing Light")
@Types({Type.ENCHANTMENT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = JourneyIntoNyx.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class BanishingLight extends Card
{
	public static final class BanishingLightAbility0 extends EventTriggeredAbility
	{
		public BanishingLightAbility0(GameState state)
		{
			super(state, "When Banishing Light enters the battlefield, exile target nonland permanent an opponent controls until Banishing Light leaves the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator legalTargets = RelativeComplement.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.LAND));
			SetGenerator target = targetedBy(this.addTarget(legalTargets, "target nonland permanent an opponent controls"));

			state.ensureTracker(new LeftTheBattlefield.LeavesTheBattlefieldTracker());
			SetGenerator thisIsGone = Intersect.instance(ABILITY_SOURCE_OF_THIS, LeftTheBattlefield.instance());

			EventFactory exileUntil = new EventFactory(EventType.EXILE_UNTIL, "Exile target nonland permanent an opponent controls until Banishing Light leaves the battlefield.");
			exileUntil.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exileUntil.parameters.put(EventType.Parameter.OBJECT, target);
			exileUntil.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(thisIsGone));
			this.addEffect(exileUntil);
		}
	}

	public BanishingLight(GameState state)
	{
		super(state);

		// When Banishing Light enters the battlefield, exile target nonland
		// permanent an opponent controls until Banishing Light leaves the
		// battlefield. (That permanent returns under its owner's control.)
		this.addAbility(new BanishingLightAbility0(state));
	}
}
