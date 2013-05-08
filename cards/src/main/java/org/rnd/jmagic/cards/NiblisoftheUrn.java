package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Niblis of the Urn")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class NiblisoftheUrn extends Card
{
	public static final class NiblisoftheUrnAbility1 extends EventTriggeredAbility
	{
		public NiblisoftheUrnAbility1(GameState state)
		{
			super(state, "Whenever Niblis of the Urn attacks, you may tap target creature.");
			this.addPattern(whenThisAttacks());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(youMay(tap(target, "Tap target creature.")));
		}
	}

	public NiblisoftheUrn(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Niblis of the Urn attacks, you may tap target creature.
		this.addAbility(new NiblisoftheUrnAbility1(state));
	}
}
