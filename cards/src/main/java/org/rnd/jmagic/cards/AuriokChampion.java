package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Auriok Champion")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("WW")
@Printings({@Printings.Printed(ex = FifthDawn.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class AuriokChampion extends Card
{
	public static final class AuriokChampionAbility1 extends EventTriggeredAbility
	{
		public AuriokChampionAbility1(GameState state)
		{
			super(state, "Whenever another creature enters the battlefield, you may gain 1 life.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), RelativeComplement.instance(HasType.instance(Type.CREATURE), ABILITY_SOURCE_OF_THIS), false));
			this.addEffect(youMay(gainLife(You.instance(), 1, "You gain 1 life."), "You may gain 1 life."));
		}
	}

	public AuriokChampion(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Protection from black and from red
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, HasColor.instance(Color.BLACK, Color.RED), "black and from red"));

		// Whenever another creature enters the battlefield, you may gain 1
		// life.
		this.addAbility(new AuriokChampionAbility1(state));
	}
}
