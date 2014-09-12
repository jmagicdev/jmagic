package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Soul of Innistrad")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class SoulofInnistrad extends Card
{
	public static final class SoulofInnistradAbility1 extends ActivatedAbility
	{
		public SoulofInnistradAbility1(GameState state)
		{
			super(state, "(3)(B)(B): Return up to three target creature cards from your graveyard to your hand.");
			this.setManaCost(new ManaPool("(3)(B)(B)"));

			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
			Target deadTarget = this.addTarget(deadThings, "three target creature cards from your graveyard");
			deadTarget.setNumber(0, 3);
			SetGenerator target = targetedBy(deadTarget);
			this.addEffect(putIntoHand(target, You.instance(), "Return up to three target creature cards from your graveyard to your hand."));
		}
	}

	public static final class SoulofInnistradAbility2 extends ActivatedAbility
	{
		public SoulofInnistradAbility2(GameState state)
		{
			super(state, "(3)(B)(B), Exile Soul of Innistrad from your graveyard: Return up to three target creature cards from your graveyard to your hand.");
			this.setManaCost(new ManaPool("(3)(B)(B)"));
			this.addCost(exile(ABILITY_SOURCE_OF_THIS, "Exile Soul of Innistrad from your graveyard"));
			this.activateOnlyFromGraveyard();

			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
			Target deadTarget = this.addTarget(deadThings, "three target creature cards from your graveyard");
			deadTarget.setNumber(0, 3);
			SetGenerator target = targetedBy(deadTarget);
			this.addEffect(putIntoHand(target, You.instance(), "Return up to three target creature cards from your graveyard to your hand."));
		}
	}

	public SoulofInnistrad(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Deathtouch
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// (3)(B)(B): Return up to three target creature cards from your
		// graveyard to your hand.
		this.addAbility(new SoulofInnistradAbility1(state));

		// (3)(B)(B), Exile Soul of Innistrad from your graveyard: Return up to
		// three target creature cards from your graveyard to your hand.
		this.addAbility(new SoulofInnistradAbility2(state));
	}
}
