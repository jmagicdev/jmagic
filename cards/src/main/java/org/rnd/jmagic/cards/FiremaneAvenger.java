package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Firemane Avenger")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("2RW")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class FiremaneAvenger extends Card
{
	public static final class FiremaneAvengerAbility1 extends EventTriggeredAbility
	{
		public FiremaneAvengerAbility1(GameState state)
		{
			super(state, "Whenever Firemane Avenger and at least two other creatures attack, Firemane Avenger deals 3 damage to target creature or player and you gain 3 life.");
			this.addPattern(battalion());

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(3, targetedBy(target), "Firemane Avenger deals 3 damage to target creature or player"));
			this.addEffect(gainLife(You.instance(), 3, "and you gain 3 life."));
		}
	}

	public FiremaneAvenger(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Battalion \u2014 Whenever Firemane Avenger and at least two other
		// creatures attack, Firemane Avenger deals 3 damage to target creature
		// or player and you gain 3 life.
		this.addAbility(new FiremaneAvengerAbility1(state));
	}
}
