package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Avacyn, Angel of Hope")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("5WWW")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class AvacynAngelofHope extends Card
{
	public static final class AvacynAngelofHopeAbility1 extends StaticAbility
	{
		public AvacynAngelofHopeAbility1(GameState state)
		{
			super(state, "Other permanents you control have indestructible.");

			SetGenerator otherPermanentsYouControl = RelativeComplement.instance(ControlledBy.instance(You.instance()), This.instance());
			this.addEffectPart(addAbilityToObject(otherPermanentsYouControl, org.rnd.jmagic.abilities.keywords.Indestructible.class));
		}
	}

	public AvacynAngelofHope(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		// Flying, vigilance, indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// Other permanents you control have indestructible.
		this.addAbility(new AvacynAngelofHopeAbility1(state));
	}
}
