package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ulamog, the Infinite Gyre")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ELDRAZI})
@ManaCost("(11)")
@Printings({@Printings.Printed(ex = Expansion.FTV_LEGENDS, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.MYTHIC)})
@ColorIdentity({})
public final class UlamogtheInfiniteGyre extends Card
{
	public static final class WhenYouCastDestroyTargetPermanent extends EventTriggeredAbility
	{
		public WhenYouCastDestroyTargetPermanent(GameState state)
		{
			super(state, "When you cast Ulamog, the Infinite Gyre, destroy target permanent.");
			this.triggersFromStack();

			this.addPattern(whenYouCastThisSpell());

			Target target = this.addTarget(Permanents.instance(), "target permanent");

			this.addEffect(destroy(targetedBy(target), "Destroy target permanent"));
		}
	}

	public UlamogtheInfiniteGyre(GameState state)
	{
		super(state);

		this.setPower(10);
		this.setToughness(10);

		// When you cast Ulamog, the Infinite Gyre, destroy target permanent.
		this.addAbility(new WhenYouCastDestroyTargetPermanent(state));

		// Annihilator 4
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Annihilator.Final(state, 4));

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// When Ulamog is put into a graveyard from anywhere, its owner shuffles
		// his or her graveyard into his or her library.
		this.addAbility(new org.rnd.jmagic.abilities.EldraziReshuffle(state, "Ulamog"));
	}
}
