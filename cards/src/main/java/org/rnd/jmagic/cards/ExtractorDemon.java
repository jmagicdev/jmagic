package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Extractor Demon")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class ExtractorDemon extends Card
{
	public static final class Extract extends EventTriggeredAbility
	{
		public Extract(GameState state)
		{
			super(state, "Whenever another creature leaves the battlefield, you may have target player put the top two cards of his or her library into his or her graveyard.");

			SimpleZoneChangePattern death = new SimpleZoneChangePattern(Battlefield.instance(), null, CreaturePermanents.instance(), true);
			this.addPattern(death);

			Target t = this.addTarget(Players.instance(), "target player");

			EventFactory mill = millCards(targetedBy(t), 2, "Target player puts the top two cards of his or her library into his or her graveyard");
			this.addEffect(youMay(mill, "You may have target player put the top two cards of his or her library into his or her graveyard."));
		}
	}

	public ExtractorDemon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever another creature leaves the battlefield, you may have target
		// player put the top two cards of his or her library into his or her
		// graveyard.
		this.addAbility(new Extract(state));

		// Unearth (2)(B)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unearth(state, "(2)(B)"));
	}
}
