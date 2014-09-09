package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sepulchral Primordial")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("5BB")
@ColorIdentity({Color.BLACK})
public final class SepulchralPrimordial extends Card
{
	public static final class SepulchralPrimordialAbility1 extends EventTriggeredAbility
	{
		public SepulchralPrimordialAbility1(GameState state)
		{
			super(state, "When Sepulchral Primordial enters the battlefield, for each opponent, you may put up to one target creature card from that player's graveyard onto the battlefield under your control.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator inYards = InZone.instance(GraveyardOf.instance(OpponentsOf.instance(You.instance())));
			SetGenerator creaturesInYards = Intersect.instance(HasType.instance(Type.CREATURE), inYards);
			Target t = new DiluvianPrimordial.DiluvianTarget(creaturesInYards, "up to one target creature card from each player's graveyard");
			this.addTarget(t);

			EventFactory rise = putOntoBattlefield(targetedBy(t), "Put up to one target creature card from that player's graveyard onto the battlefield under your control");
			this.addEffect(youMay(rise, "You may put up to one target creature card from that player's graveyard onto the battlefield under your control."));
		}
	}

	public SepulchralPrimordial(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Intimidate
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));

		// When Sepulchral Primordial enters the battlefield, for each opponent,
		// you may put up to one target creature card from that player's
		// graveyard onto the battlefield under your control.
		this.addAbility(new SepulchralPrimordialAbility1(state));
	}
}
