package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Possessed Nomad")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.HORROR, SubType.NOMAD})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.TORMENT, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class PossessedNomad extends Card
{
	public static final class KillStuff extends ActivatedAbility
	{
		public KillStuff(GameState state)
		{
			super(state, "(2)(B), (T): Destroy target white creature.");
			this.setManaCost(new ManaPool("2B"));
			this.costsTap = true;

			SetGenerator whiteCreatures = Intersect.instance(HasColor.instance(Color.WHITE), CreaturePermanents.instance());
			SetGenerator target = targetedBy(this.addTarget(whiteCreatures, "target white creature"));
			this.addEffect(destroy(target, "Destroy target white creature."));
		}
	}

	public static final class PossessedNomadAbility1 extends StaticAbility
	{
		public PossessedNomadAbility1(GameState state)
		{
			super(state, "As long as seven or more cards are in your graveyard, Possessed Nomad gets +1/+1, is black, and has \"(2)(B), (T): Destroy target white creature.\"");
			this.canApply = Both.instance(this.canApply, Threshold.instance());

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +1, +1));

			ContinuousEffect.Part black = new ContinuousEffect.Part(ContinuousEffectType.SET_COLOR);
			black.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			black.parameters.put(ContinuousEffectType.Parameter.COLOR, Identity.instance(Color.BLACK));
			this.addEffectPart(black);

			ContinuousEffect.Part killStuff = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
			killStuff.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			killStuff.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(KillStuff.class)));
			this.addEffectPart(killStuff);
		}
	}

	public PossessedNomad(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// As long as seven or more cards are in your graveyard, Possessed Nomad
		// gets +1/+1, is black, and has
		// "(2)(B), (T): Destroy target white creature."
		this.addAbility(new PossessedNomadAbility1(state));
	}
}
