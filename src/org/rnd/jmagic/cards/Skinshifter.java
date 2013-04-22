package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Skinshifter")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Skinshifter extends Card
{
	public static final class SkinshifterAbility0 extends ActivatedAbility
	{
		public SkinshifterAbility0(GameState state)
		{
			super(state, "(G): Choose one \u2014 Until end of turn, Skinshifter becomes a 4/4 Rhino and gains trample; or until end of turn, Skinshifter becomes a 2/2 Bird and gains flying; or until end of turn, Skinshifter becomes a 0/8 Plant. Activate this ability only once each turn.");
			this.setManaCost(new ManaPool("(G)"));

			Animator rhino = new Animator(ABILITY_SOURCE_OF_THIS, 4, 4);
			rhino.addSubType(SubType.RHINO);
			rhino.addAbility(org.rnd.jmagic.abilities.keywords.Trample.class);
			rhino.removeOldTypes();
			this.addEffect(1, createFloatingEffect("Until end of turn, Skinshifter becomes a 4/4 Rhino and gains trample", rhino.getParts()));

			Animator bird = new Animator(ABILITY_SOURCE_OF_THIS, 2, 2);
			bird.addSubType(SubType.BIRD);
			bird.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			bird.removeOldTypes();
			this.addEffect(2, createFloatingEffect("until end of turn, Skinshifter becomes a 2/2 Bird and gains flying", bird.getParts()));

			Animator plant = new Animator(ABILITY_SOURCE_OF_THIS, 0, 8);
			plant.addSubType(SubType.PLANT);
			plant.removeOldTypes();
			this.addEffect(3, createFloatingEffect("until end of turn, Skinshifter becomes a 2/2 Bird and gains flying; or until end of turn, Skinshifter becomes a 0/8 Plant.", plant.getParts()));

			this.perTurnLimit(1);
		}
	}

	public Skinshifter(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (G): Choose one \u2014 Until end of turn, Skinshifter becomes a 4/4
		// Rhino and gains trample; or until end of turn, Skinshifter becomes a
		// 2/2 Bird and gains flying; or until end of turn, Skinshifter becomes
		// a 0/8 Plant. Activate this ability only once each turn.
		this.addAbility(new SkinshifterAbility0(state));
	}
}
