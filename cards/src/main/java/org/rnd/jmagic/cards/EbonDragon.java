package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ebon Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("5BB")
@Printings({@Printings.Printed(ex = FromTheVaultDragons.class, r = Rarity.RARE), @Printings.Printed(ex = Portal.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class EbonDragon extends Card
{
	public static final class EbonDragonAbility1 extends EventTriggeredAbility
	{
		public EbonDragonAbility1(GameState state)
		{
			super(state, "When Ebon Dragon enters the battlefield, you may have target opponent discard a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			this.addEffect(youMay(discardCards(target, 1, "Have target opponent discard a card"), "You may have target opponent discard a card."));
		}
	}

	public EbonDragon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Ebon Dragon enters the battlefield, you may have target opponent
		// discard a card.
		this.addAbility(new EbonDragonAbility1(state));
	}
}
