package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ravenous Demon")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
@BackFace(ArchdemonofGreed.class)
public final class RavenousDemon extends Card
{
	public static final class RavenousDemonAbility0 extends ActivatedAbility
	{
		public RavenousDemonAbility0(GameState state)
		{
			super(state, "Sacrifice a Human: Transform Ravenous Demon. Activate this ability only any time you could cast a sorcery.");
			// Sacrifice a Human
			this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.HUMAN), "Sacrifice a Human"));
			this.addEffect(transform(ABILITY_SOURCE_OF_THIS, "Transform Ravenous Demon."));
			this.activateOnlyAtSorcerySpeed();
		}
	}

	public RavenousDemon(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Sacrifice a Human: Transform Ravenous Demon. Activate this ability
		// only any time you could cast a sorcery.
		this.addAbility(new RavenousDemonAbility0(state));
	}
}
