package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Grave Pact")
@Types({Type.ENCHANTMENT})
@ManaCost("1BBB")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.STRONGHOLD, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class GravePact extends Card
{
	public static final class SharingIsCaring extends EventTriggeredAbility
	{
		public SharingIsCaring(GameState state)
		{
			super(state, "Whenever a creature you control does, each other player sacrifices a creature.");

			this.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(You.instance())), true));

			SetGenerator eachOtherPlayer = RelativeComplement.instance(Players.instance(), You.instance());

			this.addEffect(sacrifice(eachOtherPlayer, 1, CreaturePermanents.instance(), "Each other player sacrifices a creature."));
		}
	}

	public GravePact(GameState state)
	{
		super(state);

		this.addAbility(new SharingIsCaring(state));
	}
}
