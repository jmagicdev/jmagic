package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Archon of the Triumvirate")
@Types({Type.CREATURE})
@SubTypes({SubType.ARCHON})
@ManaCost("5WU")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class ArchonoftheTriumvirate extends Card
{
	public static final class ArchonoftheTriumvirateAbility1 extends EventTriggeredAbility
	{
		public ArchonoftheTriumvirateAbility1(GameState state)
		{
			super(state, "Whenever Archon of the Triumvirate attacks, detain up to two target nonland permanents your opponents control.");
			this.addPattern(whenThisAttacks());

			Target target = this.addTarget(Intersect.instance(RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND)), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "up to two target nonland permanents your opponents control");
			target.setNumber(0, 2);

			this.addEffect(detain(targetedBy(target), "Detain up to two target nonland permanents your opponents control."));

			state.ensureTracker(new DetainGenerator.Tracker());
		}
	}

	public ArchonoftheTriumvirate(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Archon of the Triumvirate attacks, detain up to two target
		// nonland permanents your opponents control. (Until your next turn,
		// those permanents can't attack or block and their activated abilities
		// can't be activated.)
		this.addAbility(new ArchonoftheTriumvirateAbility1(state));
	}
}
