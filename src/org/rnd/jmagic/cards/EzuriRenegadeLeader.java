package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ezuri, Renegade Leader")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.WARRIOR})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class EzuriRenegadeLeader extends Card
{
	public static final class EzuriRenegadeLeaderAbility0 extends ActivatedAbility
	{
		public EzuriRenegadeLeaderAbility0(GameState state)
		{
			super(state, "(G): Regenerate another target Elf.");
			this.setManaCost(new ManaPool("(G)"));
			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(Intersect.instance(HasSubType.instance(SubType.ELF), Permanents.instance()), ABILITY_SOURCE_OF_THIS), "another target Elf"));
			this.addEffect(regenerate(target, "Regenerate another target Elf."));
		}
	}

	public static final class EzuriRenegadeLeaderAbility1 extends ActivatedAbility
	{
		public EzuriRenegadeLeaderAbility1(GameState state)
		{
			super(state, "(2)(G)(G)(G): Elf creatures you control get +3/+3 and gain trample until end of turn.");
			this.setManaCost(new ManaPool("(2)(G)(G)(G)"));
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(Intersect.instance(HasSubType.instance(SubType.ELF), CREATURES_YOU_CONTROL), +3, +3, "Elf creatures you control get +3/+3 and gain trample until end of turn.", org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public EzuriRenegadeLeader(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (G): Regenerate another target Elf.
		this.addAbility(new EzuriRenegadeLeaderAbility0(state));

		// (2)(G)(G)(G): Elf creatures you control get +3/+3 and gain trample
		// until end of turn.
		this.addAbility(new EzuriRenegadeLeaderAbility1(state));
	}
}
