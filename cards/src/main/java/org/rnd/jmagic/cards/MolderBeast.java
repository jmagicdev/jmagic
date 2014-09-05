package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Molder Beast")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class MolderBeast extends Card
{
	public static final class MolderBeastAbility1 extends EventTriggeredAbility
	{
		public MolderBeastAbility1(GameState state)
		{
			super(state, "Whenever an artifact is put into a graveyard from the battlefield, Molder Beast gets +2/+0 until end of turn.");
			this.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), HasType.instance(Type.ARTIFACT), true));
			this.addEffect(createFloatingEffect("Molder Beast gets +2/+0 until end of turn.", modifyPowerAndToughness(ABILITY_SOURCE_OF_THIS, +2, +0)));
		}
	}

	public MolderBeast(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever an artifact is put into a graveyard from the battlefield,
		// Molder Beast gets +2/+0 until end of turn.
		this.addAbility(new MolderBeastAbility1(state));
	}
}
