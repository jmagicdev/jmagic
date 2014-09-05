package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ensnaring Bridge")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Stronghold.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class EnsnaringBridge extends Card
{
	public static final class EnsnaringBridgeAbility0 extends StaticAbility
	{
		public EnsnaringBridgeAbility0(GameState state)
		{
			super(state, "Creatures with power greater than the number of cards in your hand can't attack.");

			SetGenerator numberOfCardsInYourHand = Count.instance(InZone.instance(HandOf.instance(You.instance())));
			SetGenerator hasPowerGreater = HasPower.instance(Between.instance(Sum.instance(Union.instance(numberGenerator(1), numberOfCardsInYourHand)), Empty.instance()));
			SetGenerator restriction = Intersect.instance(Attacking.instance(), hasPowerGreater);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(part);
		}
	}

	public EnsnaringBridge(GameState state)
	{
		super(state);

		// Creatures with power greater than the number of cards in your hand
		// can't attack.
		this.addAbility(new EnsnaringBridgeAbility0(state));
	}
}
