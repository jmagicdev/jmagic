package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Treasury Thrull")
@Types({Type.CREATURE})
@SubTypes({SubType.THRULL})
@ManaCost("4WB")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class TreasuryThrull extends Card
{
	public static final class TreasuryThrullAbility1 extends EventTriggeredAbility
	{
		public TreasuryThrullAbility1(GameState state)
		{
			super(state, "Whenever Treasury Thrull attacks, you may return target artifact, creature, or enchantment card from your graveyard to your hand.");
			this.addPattern(whenThisAttacks());

			SetGenerator types = HasType.instance(Type.ARTIFACT, Type.CREATURE, Type.ENCHANTMENT);
			SetGenerator inYourGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));
			SetGenerator restriction = Intersect.instance(types, Cards.instance(), inYourGraveyard);
			SetGenerator target = targetedBy(this.addTarget(restriction, "target artifact, creature, or enchantment card"));

			EventFactory bounce = bounce(target, "Return target artifact, creature, or enchantment card from your graveyard to your hand");
			this.addEffect(youMay(bounce, "You may return target artifact, creature, or enchantment card from your graveyard to your hand."));
		}
	}

	public TreasuryThrull(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Extort (Whenever you cast a spell, you may pay (w/b). If you do, each
		// opponent loses 1 life and you gain that much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Extort(state));

		// Whenever Treasury Thrull attacks, you may return target artifact,
		// creature, or enchantment card from your graveyard to your hand.
		this.addAbility(new TreasuryThrullAbility1(state));
	}
}
